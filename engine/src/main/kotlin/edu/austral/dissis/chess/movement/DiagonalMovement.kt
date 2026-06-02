package edu.austral.dissis.chess.movement
import kotlin.math.abs

class DiagonalMovement : Movement {
    override fun checkTrajectory(
        origin: Position,
        destination: Position,
    ): Boolean {
        val rowDiff = abs(origin.row - destination.row)
        val colDiff = abs(origin.col - destination.col)

        return rowDiff == colDiff && rowDiff > 0
    }
}
