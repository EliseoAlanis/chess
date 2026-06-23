package edu.austral.dissis.checkers.movement.effect

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.effect.PostMoveEffect

class CheckersJumpCaptureEffect(
    private val capturedPiecePosition: Position,
) : PostMoveEffect {
    override fun apply(
        previousBoard: Board,
        currentBoard: Board,
        path: Path,
    ): Board {
        val newPositions = currentBoard.positions - capturedPiecePosition
        return currentBoard.copy(positions = newPositions)
    }
}
