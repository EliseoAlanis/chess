package edu.austral.dissis.chess.movement.composite

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Position

class CompositeMovement(
    private val movements: List<Movement>,
) : Movement {
    override fun checkTrajectory(
        origin: Position,
        destination: Position,
    ): Boolean = movements.any { it.checkTrajectory(origin, destination) }
}
