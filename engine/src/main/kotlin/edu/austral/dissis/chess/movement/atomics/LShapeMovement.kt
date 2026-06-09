package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.chess.movement.Movement
import edu.austral.dissis.chess.movement.Position
import kotlin.math.abs

class LShapeMovement(
    private val longEdge: Int = Int.MAX_VALUE,
    private val shortEdge: Int = Int.MAX_VALUE,
) : Movement {
    override fun checkTrajectory(
        origin: Position,
        destination: Position,
    ): Boolean {
        val rowDiff = abs(origin.row - destination.row)
        val colDiff = abs(origin.col - destination.col)

        val isVerticalL = rowDiff == longEdge && colDiff == shortEdge
        val isHorizontalL = rowDiff == shortEdge && colDiff == longEdge

        return isVerticalL || isHorizontalL
    }
}
