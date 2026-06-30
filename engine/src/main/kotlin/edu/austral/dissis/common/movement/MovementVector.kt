package edu.austral.dissis.common.movement

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

data class MovementVector(
    val origin: Position,
    val destination: Position,
) {
    val rowDelta: Int get() = destination.row - origin.row
    val colDelta: Int get() = destination.col - origin.col

    val rowDiff: Int get() = abs(rowDelta)
    val colDiff: Int get() = abs(colDelta)

    val rowDirection: Int get() = rowDelta.sign
    val colDirection: Int get() = colDelta.sign

    val isDiagonal: Boolean get() = rowDiff == colDiff
    val isStraight: Boolean get() = rowDiff == 0 || colDiff == 0
    val isLShaped: Boolean get() = isLShaped(2, 1)

    fun isLShaped(
        longEdge: Int,
        shortEdge: Int,
    ): Boolean = (rowDiff == longEdge && colDiff == shortEdge) || (rowDiff == shortEdge && colDiff == longEdge)

    val distance: Int get() = max(rowDiff, colDiff)

    fun getIntermediatePositions(): List<Position> {
        val steps = max(rowDiff, colDiff) - 1
        if (steps <= 0) return emptyList()

        return (1..steps).map { i ->
            Position(
                origin.row + (rowDirection * i),
                origin.col + (colDirection * i),
            )
        }
    }
}
