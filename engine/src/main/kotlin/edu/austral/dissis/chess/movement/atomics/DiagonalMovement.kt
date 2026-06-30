package edu.austral.dissis.chess.movement.atomics
import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import kotlin.math.abs

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class DiagonalMovement(
    private val maxSteps: Int = Int.MAX_VALUE,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val path = context.path
        val rowDiff = abs(path.origin.row - path.destination.row)
        val colDiff = abs(path.origin.col - path.destination.col)
        
        val isValid = rowDiff == colDiff && path.origin != path.destination && rowDiff <= maxSteps
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
