package edu.austral.dissis.chess.movement.effect

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.effect.PostMoveEffect

class EnPassantCaptureEffect(
    private val capturedPawnPosition: Position,
) : PostMoveEffect {
    override fun apply(
        previousBoard: Board,
        currentBoard: Board,
        vector: MovementVector,
    ): Board {
        val newPositions = currentBoard.positions - capturedPawnPosition
        return currentBoard.copy(positions = newPositions)
    }
}
