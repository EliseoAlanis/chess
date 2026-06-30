package edu.austral.dissis.checkers.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.MovementVector
import kotlin.math.abs

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class CheckersDiagonalMovement(
    private val maxSteps: Int = 1,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val vector = context.vector
        val rowDiff = abs(vector.origin.row - vector.destination.row)
        val colDiff = abs(vector.origin.col - vector.destination.col)
        val isValid = rowDiff == colDiff && rowDiff == maxSteps
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
