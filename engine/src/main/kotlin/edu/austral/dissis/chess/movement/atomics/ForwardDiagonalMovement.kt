package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.chess.movement.Movement
import edu.austral.dissis.chess.movement.Position
import kotlin.math.abs

class ForwardDiagonalMovement(
    private val direction: Int
) : Movement {
    override fun checkTrajectory(origin: Position, destination: Position): Boolean {
        val rowDiff = destination.row - origin.row
        val colDiff = abs(destination.col - origin.col)

        return rowDiff == direction && colDiff == 1
    }
}
