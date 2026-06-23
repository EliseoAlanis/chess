package edu.austral.dissis.chess.movement.atomics
import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import kotlin.math.abs

class DiagonalMovement(
    private val maxSteps: Int = Int.MAX_VALUE,
) : Movement {
    override fun checkTrajectory(path: Path): Boolean {
        val rowDiff = abs(path.origin.row - path.destination.row)
        val colDiff = abs(path.origin.col - path.destination.col)

        return rowDiff == colDiff && rowDiff > 0 && rowDiff <= maxSteps
    }
}
