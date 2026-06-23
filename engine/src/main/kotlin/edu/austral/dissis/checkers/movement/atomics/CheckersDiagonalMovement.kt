package edu.austral.dissis.checkers.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import kotlin.math.abs

class CheckersDiagonalMovement(
    private val maxSteps: Int = 1
) : Movement {
    override fun checkTrajectory(path: Path): Boolean {
        val rowDiff = abs(path.origin.row - path.destination.row)
        val colDiff = abs(path.origin.col - path.destination.col)

        return rowDiff == colDiff && rowDiff == maxSteps
    }
}
