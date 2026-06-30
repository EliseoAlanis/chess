package edu.austral.dissis.chess.network

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.common.game.GameEngine
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import edu.austral.ingsis.clientserver.Server
import edu.austral.ingsis.clientserver.ServerConnectionListener
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder

class ChessServer(
    private var engine: GameEngine,
) {
    private val server: Server
    private val clients = mutableListOf<String>()

    init {
        server =
            NettyServerBuilder
                .createDefault()
                .withPort(SERVER_PORT)
                .withConnectionListener(
                    object : ServerConnectionListener {
                        override fun handleClientConnection(clientId: String) {
                            println("Cliente conectado al server: \$clientId")
                            val assignedColor = when (clients.size) {
                                0 -> "WHITE"
                                1 -> "BLACK"
                                else -> "SPECTATOR"
                            }
                            clients.add(clientId)

                            val initialPayload = InitialConnectionPayload(assignedColor, buildGameStatePayload(engine))
                            server.sendMessage(clientId, Message("initial_state", initialPayload))

                            broadcastState()
                        }

                        override fun handleClientConnectionClosed(clientId: String) {
                            println("Cliente desconectado del server: \$clientId")
                            clients.remove(clientId)
                        }
                    },
                ).addMessageListener("move",
                    object : TypeReference<Message<MovePayload>>() {}, MoveListener())
                .addMessageListener("undo",
                    object : TypeReference<Message<UndoPayload>>() {}, UndoListener())
                .addMessageListener("redo",
                    object : TypeReference<Message<RedoPayload>>() {}, RedoListener())
                .build()
    }

    fun start() {
        server.start()
        println("Servidor Netty iniciado en puerto \$SERVER_PORT")
    }

    fun broadcastState() {
        // AcÃ¡ se envÃ­a el estado de todo el tablero a los clientes conectados
        val payload = buildGameStatePayload(engine)
        server.broadcast(Message("new_state", payload))
    }

    private fun buildGameStatePayload(engine: GameEngine): GameStatePayload {
        val pieces =
            engine.currentGame.board.positions.map { (pos, piece) ->
                PiecePayload(
                    id = piece.id,
                    color = piece.color.name,
                    row = pos.row,
                    col = pos.col,
                    pieceId = piece.pieceType.lowercase(),
                )
            }
        val status = engine.currentGame.status
        val isGameOver = status != edu.austral.dissis.common.game.GameStatus.IN_PROGRESS
        val winnerColor = when (status) {
            edu.austral.dissis.common.game.GameStatus.WHITE_WINS -> "WHITE"
            edu.austral.dissis.common.game.GameStatus.BLACK_WINS -> "BLACK"
            else -> null
        }
        return GameStatePayload(
            pieces = pieces,
            currentPlayer = engine.currentGame.turnManager.currentTurn.name,
            boardCols = engine.currentGame.board.size.columns,
            boardRows = engine.currentGame.board.size.rows,
            isGameOver = isGameOver,
            winnerColor = winnerColor,
            canUndo = engine.canUndo(),
            canRedo = engine.canRedo()
        )
    }

    private inner class MoveListener : MessageListener<MovePayload> {
        override fun handleMessage(message: Message<MovePayload>) {
            val payload = message.payload
            val vector =
                MovementVector(
                    Position(payload.fromRow, payload.fromCol),
                    Position(payload.toRow, payload.toCol),
                )

            val (nextEngine, result) = engine.applyMove(vector)
            if (result is MoveResult.Success) {
                engine = nextEngine
                broadcastState()
            } else if (result is MoveResult.Failure) {
                // Se podrÃ­a notificar al cliente del fallo
                println("Movimiento invÃ¡lido: \${result.reason}")
                broadcastState()
            }
        }
    }

    private inner class UndoListener : MessageListener<UndoPayload> {
        override fun handleMessage(message: Message<UndoPayload>) {
            if (engine.canUndo()) {
                engine = engine.undo()
                broadcastState()
            }
        }
    }

    private inner class RedoListener : MessageListener<RedoPayload> {
        override fun handleMessage(message: Message<RedoPayload>) {
            if (engine.canRedo()) {
                engine = engine.redo()
                broadcastState()
            }
        }
    }

    companion object {
        private const val SERVER_PORT = 8080
    }
}
