package edu.austral.dissis.checkers.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import kotlin.math.abs

class CheckersForwardDiagonalMovement(
    private val direction: Int,
    private val maxSteps: Int = 1,
) : Movement {
    override fun checkTrajectory(path: Path): Boolean {
        val rowDiff = path.destination.row - path.origin.row
        val colDiff = abs(path.destination.col - path.origin.col)

        // For simple move maxSteps is 1, so rowDiff == direction and colDiff == 1
        // For jump maxSteps is 2, so rowDiff == direction * 2 and colDiff == 2
        return rowDiff == direction * maxSteps && colDiff == maxSteps
    }
}
