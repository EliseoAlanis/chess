package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import kotlin.math.abs

class LShapeMovement(
    private val longEdge: Int = Int.MAX_VALUE,
    private val shortEdge: Int = Int.MAX_VALUE,
) : Movement {
    override fun checkTrajectory(path: Path): Boolean {
        val rowDiff = abs(path.origin.row - path.destination.row)
        val colDiff = abs(path.origin.col - path.destination.col)

        val isVerticalL = rowDiff == longEdge && colDiff == shortEdge
        val isHorizontalL = rowDiff == shortEdge && colDiff == longEdge

        return isVerticalL || isHorizontalL
    }

    override fun getIntermediatePositions(path: Path): List<Position> = emptyList()
}
