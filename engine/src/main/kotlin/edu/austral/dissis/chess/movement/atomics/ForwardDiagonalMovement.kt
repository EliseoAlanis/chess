package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path
import kotlin.math.abs

class ForwardDiagonalMovement(
    private val direction: Int,
) : Movement {
    override fun checkTrajectory(
        path: Path,
    ): Boolean {
        val rowDiff = path.destination.row - path.origin.row
        val colDiff = abs(path.destination.col - path.origin.col)

        return rowDiff == direction && colDiff == 1
    }
}
