package edu.austral.dissis.common.pieces

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.Rule
import edu.austral.dissis.common.rules.RuleResult

data class Piece(
    val id: String,
    val color: Color,
    val pieceType: String,
    val rules: List<Rule>,
    val captureRules: List<Rule> = rules,
) {
    fun evaluateMove(
        context: MoveContext,
        isCapture: Boolean = false,
    ): RuleResult {
        val rulesToUse = if (isCapture) captureRules else rules
        for (rule in rulesToUse) {
            val result = rule.evaluate(context)
            if (result is RuleResult.Valid) return result
        }
        return RuleResult.Invalid
    }
}
