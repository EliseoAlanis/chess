package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import kotlin.math.abs

class LShapeMovement(
    private val longEdge: Int = Int.MAX_VALUE,
    private val shortEdge: Int = Int.MAX_VALUE,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val vector = context.vector
        val isValid = vector.isLShaped(longEdge, shortEdge)
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
