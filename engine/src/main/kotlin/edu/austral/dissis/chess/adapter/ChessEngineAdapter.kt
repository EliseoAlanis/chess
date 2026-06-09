package edu.austral.dissis.chess.adapter

import edu.austral.dissis.chess.game.Game
import edu.austral.dissis.chess.game.MoveResult
import edu.austral.dissis.chess.gui.GameInputEvent
import edu.austral.dissis.chess.gui.GameState
import edu.austral.dissis.chess.gui.Move
import io.reactivex.rxjava3.core.Observable
import edu.austral.dissis.chess.gui.BoardSize as UIBoardSize

class ChessEngineAdapter(private var domainGame: Game) {

    // Transforma el flujo de eventos de la UI en un flujo de estados del juego
    fun process(inputs: Observable<GameInputEvent>): Observable<GameState> =
        inputs.map { event ->
            when (event) {
                GameInputEvent.Init -> toPlayingState()
                is GameInputEvent.MoveEvent -> handleMove(event.move)
                GameInputEvent.Undo -> toPlayingState() // Ignorado por ahora
                GameInputEvent.Redo -> toPlayingState() // Ignorado por ahora
            }
        }

    private fun handleMove(move: Move): GameState {
        val origin = move.from.toDomainPosition()
        val dest = move.to.toDomainPosition()

        // Llamamos a tu motor inmutable
        return when (val result = domainGame.playMove(origin, dest)) {
            is MoveResult.Success -> {
                // Actualizamos el estado interno del Adapter
                domainGame = result.newGameState
                toPlayingState() // Emitimos el tablero actualizado
            }
            is MoveResult.Failure -> {
                // Emitimos el error para que la UI rebote la pieza
                GameState.InvalidMove(result.reason)
            }
        }
    }

    private fun toPlayingState(): GameState.Playing {
        return GameState.Playing(
            boardSize = UIBoardSize(domainGame.board.size.columns, domainGame.board.size.rows),
            pieces = domainGame.board.toUIPieces(),
            currentPlayer = domainGame.turnManager.currentTurn.toUIColor(),
            canUndo = false, // Lo dejamos en false hasta que implementes el Undo en tu dominio
            canRedo = false
        )
    }
}
