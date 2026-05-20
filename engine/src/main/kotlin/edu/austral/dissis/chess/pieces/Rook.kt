package edu.austral.dissis.chess.pieces

import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.movement.StraightMovement

data class Rook(
    override val color: Color,
) : Piece {
    private val straightMovement = StraightMovement()

    override fun isValidMovePattern(
        origin: Position,
        destination: Position,
    ): Boolean = straightMovement.checkTrajectory(origin, destination)
}
