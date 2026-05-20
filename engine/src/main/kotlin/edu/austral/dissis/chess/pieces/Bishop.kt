package edu.austral.dissis.chess.pieces

import edu.austral.dissis.chess.movement.DiagonalMovement
import edu.austral.dissis.chess.movement.Position

data class Bishop(
    override val color: Color,
) : Piece {
    private val diagonalMovement = DiagonalMovement()

    override fun isValidMovePattern(
        origin: Position,
        destination: Position,
    ): Boolean = diagonalMovement.checkTrajectory(origin, destination)
}
