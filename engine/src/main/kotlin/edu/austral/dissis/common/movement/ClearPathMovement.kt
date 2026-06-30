package edu.austral.dissis.common.movement

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position

class ClearPathMovement(
    private val movement: Movement
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val baseResult = movement.evaluate(context)
        if (baseResult is RuleResult.Invalid) return RuleResult.Invalid

        val intermediate = movement.getIntermediatePositions(context.vector)
        val isValid = intermediate.none { pos -> context.board.hasPieceAt(pos) }
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }

    override fun getIntermediatePositions(vector: MovementVector): List<Position> {
        return movement.getIntermediatePositions(vector)
    }
}
