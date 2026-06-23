package edu.austral.dissis.chess.movement.composite

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path

class CompositeMovement(
    private val movements: List<Movement>,
) : Movement {
    override fun checkTrajectory(
        path: Path,
    ): Boolean = movements.any { it.checkTrajectory(path) }

    override fun getIntermediatePositions(path: Path): List<Position> {
        val matchingMovement = movements.find { it.checkTrajectory(path) }
        return matchingMovement?.getIntermediatePositions(path) ?: emptyList()
    }
}
