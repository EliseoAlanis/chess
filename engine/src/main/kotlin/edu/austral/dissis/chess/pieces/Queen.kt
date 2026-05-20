package edu.austral.dissis.chess.pieces

import edu.austral.dissis.chess.movement.DiagonalMovement
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.movement.StraightMovement

data class Queen(
    override val color: Color,
) : Piece {
    private val straightMovement = StraightMovement()
    private val diagonalMovement = DiagonalMovement()

    override fun isValidMovePattern(
        origin: Position,
        destination: Position,
    ): Boolean =
        straightMovement.checkTrajectory(origin, destination) ||
            diagonalMovement.checkTrajectory(origin, destination)
}
