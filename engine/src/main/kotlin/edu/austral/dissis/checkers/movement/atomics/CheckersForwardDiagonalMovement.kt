package edu.austral.dissis.checkers.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import kotlin.math.abs
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class CheckersForwardDiagonalMovement(
    private val direction: Int,
    private val maxSteps: Int = 1
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val path = context.path
        val rowDiff = path.destination.row - path.origin.row
        val colDiff = abs(path.destination.col - path.origin.col)

        val isValid = rowDiff == direction * maxSteps && colDiff == maxSteps
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
