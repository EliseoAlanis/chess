package edu.austral.dissis.common.network

import edu.austral.dissis.chess.gui.BoardSize
import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.GameInputEvent
import edu.austral.dissis.chess.gui.GameState
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.chess.gui.Position
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class NetworkAdapter(
    private val client: GameClient,
    private val pieceNameMapper: (String) -> String
) {
    private val stateSubject = PublishSubject.create<GameState>()
    private var myColor: String = "SPECTATOR"
    private var currentTurn: String = "WHITE"
    private var lastGameStatePayload: GameStatePayload? = null

    init {
        client.onInitialConnection = { payload ->
            myColor = payload.assignedColor
            lastGameStatePayload = payload.gameState
            currentTurn = payload.gameState.currentPlayer
            stateSubject.onNext(mapToGameState(payload.gameState))
        }
        client.onGameStateReceived = { payload ->
            lastGameStatePayload = payload
            currentTurn = payload.currentPlayer
            stateSubject.onNext(mapToGameState(payload))
        }
    }

    fun process(inputs: Observable<GameInputEvent>): Observable<GameState> {
        inputs.subscribe { event ->
            when (event) {
                is GameInputEvent.MoveEvent -> {
                    if (myColor == "SPECTATOR") {
                        stateSubject.onNext(GameState.InvalidMove("Spectators cannot move pieces"))
                        lastGameStatePayload?.let { stateSubject.onNext(mapToGameState(it)) }
                        return@subscribe
                    }
                    if (myColor != currentTurn) {
                        stateSubject.onNext(GameState.InvalidMove("It is not your turn"))
                        lastGameStatePayload?.let { stateSubject.onNext(mapToGameState(it)) }
                        return@subscribe
                    }

                    val move = event.move
                    client.sendMove(MovePayload(
                        fromRow = move.from.row,
                        fromCol = move.from.column,
                        toRow = move.to.row,
                        toCol = move.to.column
                    ))
                }
                GameInputEvent.Init -> {
                    // El servidor nos envía el estado inicial automáticamente al conectarnos
                }
                GameInputEvent.Undo -> {
                    if (myColor != "SPECTATOR" && myColor != currentTurn) {
                        client.sendUndo()
                    } else {
                        stateSubject.onNext(GameState.InvalidMove("You cannot undo at this time"))
                        lastGameStatePayload?.let { stateSubject.onNext(mapToGameState(it)) }
                    }
                }
                GameInputEvent.Redo -> {
                    if (myColor != "SPECTATOR" && myColor == currentTurn) {
                        client.sendRedo()
                    } else {
                        stateSubject.onNext(GameState.InvalidMove("You cannot redo at this time"))
                        lastGameStatePayload?.let { stateSubject.onNext(mapToGameState(it)) }
                    }
                }
            }
        }
        return stateSubject
    }

    private fun mapToGameState(payload: GameStatePayload): GameState {
        if (payload.isGameOver && payload.winnerColor != null) {
            val winner = if (payload.winnerColor == "WHITE") PlayerColor.WHITE else PlayerColor.BLACK
            return GameState.GameOver(winner)
        }

        val boardSize = BoardSize(payload.boardCols, payload.boardRows)
        val pieces = payload.pieces.map { p ->
            ChessPiece(
                id = p.id,
                color = if (p.color == "WHITE") PlayerColor.WHITE else PlayerColor.BLACK,
                position = Position(p.row, p.col),
                pieceId = pieceNameMapper(p.pieceId)
            )
        }
        val currentPlayer = if (payload.currentPlayer == "WHITE") PlayerColor.WHITE else PlayerColor.BLACK
        val allowUndo = payload.canUndo && myColor != "SPECTATOR" && myColor != currentTurn
        val allowRedo = payload.canRedo && myColor != "SPECTATOR" && myColor == currentTurn
        
        return GameState.Playing(
            boardSize = boardSize,
            pieces = pieces,
            currentPlayer = currentPlayer,
            canUndo = allowUndo,
            canRedo = allowRedo
        )
    }
}
