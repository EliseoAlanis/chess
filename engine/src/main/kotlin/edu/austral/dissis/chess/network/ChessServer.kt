package edu.austral.dissis.chess.network

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.common.game.GameEngine
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.common.movement.Path
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
                .withPort(8080)
                .withConnectionListener(
                    object : ServerConnectionListener {
                        override fun handleClientConnection(clientId: String) {
                            println("Cliente conectado al server: \$clientId")
                            clients.add(clientId)

                            // Lógica para asignar colores:
                            // 1er cliente conectado a Netty: NEGRAS (el servidor como app juega de BLANCAS, pero el server de Netty no distingue esto).
                            // Asumiremos que la UI del servidor levanta el Server y luego levanta un Client local.
                            // El 1er cliente (local) -> BLANCAS
                            // El 2do cliente (remoto) -> NEGRAS
                            // Resto -> SPECTATOR

                            broadcastState() // Enviar estado al conectarse
                        }

                        override fun handleClientConnectionClosed(clientId: String) {
                            println("Cliente desconectado del server: \$clientId")
                            clients.remove(clientId)
                        }
                    },
                ).addMessageListener("move", object : TypeReference<Message<MovePayload>>() {}, MoveListener())
                .build()
    }

    fun start() {
        server.start()
        println("Servidor Netty iniciado en puerto 8080")
    }

    fun broadcastState() {
        // Acá se envía el estado de todo el tablero a los clientes conectados
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
        return GameStatePayload(
            pieces = pieces,
            currentPlayer = engine.currentGame.turnManager.currentTurn.name,
        )
    }

    private inner class MoveListener : MessageListener<MovePayload> {
        override fun handleMessage(message: Message<MovePayload>) {
            val payload = message.payload
            val path =
                Path(
                    Position(payload.fromRow, payload.fromCol),
                    Position(payload.toRow, payload.toCol),
                )

            val (nextEngine, result) = engine.applyMove(path)
            if (result is MoveResult.Success) {
                engine = nextEngine
                broadcastState()
            } else if (result is MoveResult.Failure) {
                // Se podría notificar al cliente del fallo
                println("Movimiento inválido: \${result.reason}")
            }
        }
    }
}
