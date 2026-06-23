package edu.austral.dissis.checkers.adapter

import edu.austral.dissis.common.game.GameEngine
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.chess.gui.GameInputEvent
import edu.austral.dissis.chess.gui.GameState
import edu.austral.dissis.chess.gui.Move
import io.reactivex.rxjava3.core.Observable
import edu.austral.dissis.chess.gui.BoardSize as UIBoardSize

class CheckersEngineAdapter(
    private val engine: GameEngine
) {
    fun process(inputs: Observable<GameInputEvent>): Observable<GameState> =
        inputs.map { event ->
            when (event) {
                GameInputEvent.Init -> toPlayingState()

                is GameInputEvent.MoveEvent -> handleMove(event.move)

                GameInputEvent.Undo -> {
                    engine.undo()
                    toPlayingState()
                }

                GameInputEvent.Redo -> {
                    engine.redo()
                    toPlayingState()
                }
            }
        }

    private fun handleMove(move: Move): GameState {
        val origin = move.from.toDomainPosition()
        val dest = move.to.toDomainPosition()
        val path = edu.austral.dissis.common.movement.Path(origin, dest)

        return when (val result = engine.applyMove(path)) {
            is MoveResult.Success -> checkForGameOver(engine.currentGame.status) ?: toPlayingState()
            is MoveResult.Failure -> GameState.InvalidMove(result.reason)
        }
    }

    private fun checkForGameOver(status: edu.austral.dissis.common.game.GameStatus): GameState? {
        val uiWhite = edu.austral.dissis.chess.gui.PlayerColor.WHITE
        val uiBlack = edu.austral.dissis.chess.gui.PlayerColor.BLACK
        return when (status) {
            edu.austral.dissis.common.game.GameStatus.WHITE_WINS -> GameState.GameOver(uiWhite)
            edu.austral.dissis.common.game.GameStatus.BLACK_WINS -> GameState.GameOver(uiBlack)
            else -> null
        }
    }

    private fun toPlayingState(): GameState.Playing {
        val game = engine.currentGame
        val board = game.board
        return GameState.Playing(
            boardSize = UIBoardSize(board.size.columns, board.size.rows),
            pieces = board.toUIPieces(),
            currentPlayer = game.turnManager.currentTurn.toUIColor(),
            canUndo = engine.canUndo(),
            canRedo = engine.canRedo(),
        )
    }
}
