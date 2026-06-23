package edu.austral.dissis.chess.network

import edu.austral.dissis.chess.gui.*
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject

class NetworkAdapter(
    private val client: ChessClient,
    private val pieceNameMapper: (String) -> String
) {
    private val stateSubject = PublishSubject.create<GameState>()

    init {
        client.onGameStateReceived = { payload ->
            stateSubject.onNext(mapToGameState(payload))
        }
    }

    fun process(inputs: Observable<GameInputEvent>): Observable<GameState> {
        inputs.subscribe { event ->
            when (event) {
                is GameInputEvent.MoveEvent -> {
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
                    // Opcional para implementar por red
                }
                GameInputEvent.Redo -> {
                    // Opcional para implementar por red
                }
            }
        }
        return stateSubject
    }

    private fun mapToGameState(payload: GameStatePayload): GameState {
        if (payload.isGameOver) {
            val winner = if (payload.winnerColor == "WHITE") PlayerColor.WHITE else PlayerColor.BLACK
            return GameState.GameOver(winner)
        }

        val boardSize = BoardSize(8, 8)
        val pieces = payload.pieces.map { p ->
            ChessPiece(
                id = p.id,
                color = if (p.color == "WHITE") PlayerColor.WHITE else PlayerColor.BLACK,
                position = Position(p.row, p.col),
                pieceId = pieceNameMapper(p.pieceId)
            )
        }
        val currentPlayer = if (payload.currentPlayer == "WHITE") PlayerColor.WHITE else PlayerColor.BLACK
        
        return GameState.Playing(
            boardSize = boardSize,
            pieces = pieces,
            currentPlayer = currentPlayer,
            canUndo = false,
            canRedo = false
        )
    }
}
