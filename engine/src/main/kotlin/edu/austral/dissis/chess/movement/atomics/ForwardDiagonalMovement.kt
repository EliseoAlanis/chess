package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import kotlin.math.abs

class ForwardDiagonalMovement(
    private val direction: Int,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val vector = context.vector
        val isValid = vector.destination.row == vector.origin.row + direction && abs(vector.destination.col - vector.origin.col) == 1
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
