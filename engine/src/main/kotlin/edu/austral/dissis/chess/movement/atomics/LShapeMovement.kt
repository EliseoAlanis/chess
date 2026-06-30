package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import kotlin.math.abs

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class LShapeMovement(
    private val longEdge: Int = Int.MAX_VALUE,
    private val shortEdge: Int = Int.MAX_VALUE,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val path = context.path
        val rowDiff = abs(path.origin.row - path.destination.row)
        val colDiff = abs(path.origin.col - path.destination.col)

        val isVerticalL = rowDiff == longEdge && colDiff == shortEdge
        val isHorizontalL = rowDiff == shortEdge && colDiff == longEdge

        val isValid = isVerticalL || isHorizontalL
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }

    override fun getIntermediatePositions(path: Path): List<Position> = emptyList()
}
