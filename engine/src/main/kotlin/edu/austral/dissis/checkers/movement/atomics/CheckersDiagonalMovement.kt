package edu.austral.dissis.checkers.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import kotlin.math.abs

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class CheckersDiagonalMovement(
    private val maxSteps: Int = 1,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val path = context.path
        val rowDiff = abs(path.origin.row - path.destination.row)
        val colDiff = abs(path.origin.col - path.destination.col)
        val isValid = rowDiff == colDiff && rowDiff == maxSteps
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
