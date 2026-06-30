package edu.austral.dissis.checkers.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import kotlin.math.abs

class CheckersForwardDiagonalMovement(
    private val direction: Int,
    private val maxSteps: Int = 1,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val vector = context.vector
        val rowDiff = vector.destination.row - vector.origin.row
        val colDiff = abs(vector.destination.col - vector.origin.col)

        val isValid = rowDiff == direction * maxSteps && colDiff == maxSteps
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
