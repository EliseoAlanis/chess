package edu.austral.dissis.chess.movement

import kotlin.math.abs

class LShapeMovement : Movement {
    override fun checkTrajectory(
        origin: Position,
        destination: Position,
    ): Boolean {
        val rowDiff = abs(origin.row - destination.row)
        val colDiff = abs(origin.col - destination.col)

        val isVerticalL = rowDiff == 2 && colDiff == 1
        val isHorizontalL = rowDiff == 1 && colDiff == 2

        return isVerticalL || isHorizontalL
    }
}
