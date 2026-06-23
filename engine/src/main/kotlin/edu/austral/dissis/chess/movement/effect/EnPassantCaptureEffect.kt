package edu.austral.dissis.chess.movement.effect

import edu.austral.dissis.common.movement.effect.PostMoveEffect
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position

class EnPassantCaptureEffect(
    private val capturedPawnPosition: Position
) : PostMoveEffect {
    override fun apply(
        previousBoard: Board,
        currentBoard: Board,
        origin: Position,
        destination: Position,
    ): Board {
        val newPositions = currentBoard.getPositions() - capturedPawnPosition
        return currentBoard.copy(positions = newPositions)
    }
}
