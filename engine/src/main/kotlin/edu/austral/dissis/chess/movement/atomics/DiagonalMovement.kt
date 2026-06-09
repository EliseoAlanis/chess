package edu.austral.dissis.chess.movement.atomics
import edu.austral.dissis.chess.movement.Movement
import edu.austral.dissis.chess.movement.Position
import kotlin.math.abs

class DiagonalMovement(
    private val maxSteps: Int = Int.MAX_VALUE,
) : Movement {
    override fun checkTrajectory(
        origin: Position,
        destination: Position,
    ): Boolean {
        val rowDiff = abs(origin.row - destination.row)
        val colDiff = abs(origin.col - destination.col)

        return rowDiff == colDiff && rowDiff > 0 && rowDiff <= maxSteps
    }
}
