package edu.austral.dissis.chess.movement.atomics
import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import kotlin.math.abs

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class DiagonalMovement(
    private val maxSteps: Int = Int.MAX_VALUE,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val vector = context.vector
        val isValid = vector.isDiagonal && vector.distance > 0 && vector.distance <= maxSteps
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
