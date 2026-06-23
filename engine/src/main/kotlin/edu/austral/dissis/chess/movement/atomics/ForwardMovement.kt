package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Position

class ForwardMovement(
    private val direction: Int,
) : Movement {
    override fun checkTrajectory(
        origin: Position,
        destination: Position,
    ): Boolean {
        val rowDiff = destination.row - origin.row
        val colDiff = destination.col - origin.col

        return colDiff == 0 && rowDiff == direction
    }
}
