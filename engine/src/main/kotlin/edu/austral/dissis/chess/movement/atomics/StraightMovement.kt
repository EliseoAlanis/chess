package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.chess.movement.Movement
import edu.austral.dissis.chess.movement.Position
import kotlin.math.abs

class StraightMovement(
    private val maxSteps: Int = Int.MAX_VALUE,
) : Movement {
    override fun checkTrajectory(
        origin: Position,
        destination: Position,
    ): Boolean {
        val movesInRow = origin.row == destination.row
        val movesInColumn = origin.col == destination.col

        val distance = maxOf(abs(origin.row - destination.row), abs(origin.col - destination.col))

        return (movesInRow || movesInColumn) && origin != destination && distance <= maxSteps
    }
}
