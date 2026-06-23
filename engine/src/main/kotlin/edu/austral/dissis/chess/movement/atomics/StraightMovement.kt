package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path
import kotlin.math.abs

class StraightMovement(
    private val maxSteps: Int = Int.MAX_VALUE,
) : Movement {
    override fun checkTrajectory(
        path: Path,
    ): Boolean {
        val movesInRow = path.origin.row == path.destination.row
        val movesInColumn = path.origin.col == path.destination.col

        val distance = maxOf(abs(path.origin.row - path.destination.row), abs(path.origin.col - path.destination.col))

        return (movesInRow || movesInColumn) && path.origin != path.destination && distance <= maxSteps
    }
}
