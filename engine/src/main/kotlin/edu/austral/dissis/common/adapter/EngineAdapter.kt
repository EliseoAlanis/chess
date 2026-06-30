package edu.austral.dissis.common.adapter

import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.GameInputEvent
import edu.austral.dissis.chess.gui.GameState
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.common.game.GameEngine
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.common.pieces.Color
import io.reactivex.rxjava3.core.Observable
import edu.austral.dissis.chess.gui.BoardSize as UIBoardSize
import edu.austral.dissis.chess.gui.Position as UIPosition
import edu.austral.dissis.common.movement.Position as DomainPosition

fun Color.toUIColor(): PlayerColor =
    when (this) {
        Color.WHITE -> PlayerColor.WHITE
        Color.BLACK -> PlayerColor.BLACK
    }

fun DomainPosition.toUIPosition(): UIPosition = UIPosition(this.row, this.col)

fun UIPosition.toDomainPosition(): DomainPosition = DomainPosition(this.row, this.column)

class EngineAdapter(
    private var engine: GameEngine,
    private val pieceNameMapper: (String) -> String,
) {
    fun process(inputs: Observable<GameInputEvent>): Observable<GameState> =
        inputs.map { event ->
            when (event) {
                GameInputEvent.Init -> {
                    toPlayingState()
                }

                is GameInputEvent.MoveEvent -> {
                    handleMove(event.move)
                }

                GameInputEvent.Undo -> {
                    engine = engine.undo()
                    toPlayingState()
                }

                GameInputEvent.Redo -> {
                    engine = engine.redo()
                    toPlayingState()
                }
            }
        }

    private fun handleMove(move: Move): GameState {
        val origin = move.from.toDomainPosition()
        val dest = move.to.toDomainPosition()
        val vector =
            edu.austral.dissis.common.movement
                .MovementVector(origin, dest)

        val (nextEngine, result) = engine.applyMove(vector)
        engine = nextEngine

        return when (result) {
            is MoveResult.Success -> checkForGameOver(engine.currentGame.status) ?: toPlayingState()
            is MoveResult.Failure -> GameState.InvalidMove(result.reason)
        }
    }

    private fun checkForGameOver(status: edu.austral.dissis.common.game.GameStatus): GameState? {
        val uiWhite = PlayerColor.WHITE
        val uiBlack = PlayerColor.BLACK
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
            pieces =
                board.positions.map { (domainPos, piece) ->
                    ChessPiece(
                        id = piece.id,
                        color = piece.color.toUIColor(),
                        position = domainPos.toUIPosition(),
                        pieceId = pieceNameMapper(piece.pieceType.lowercase()),
                    )
                },
            currentPlayer = game.turnManager.currentTurn.toUIColor(),
            canUndo = engine.canUndo(),
            canRedo = engine.canRedo(),
        )
    }
}
