package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.Rule

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class BoardLimitsRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val isValid = context.vector.destination.row in 1..context.board.size.rows &&
                      context.vector.destination.col in 1..context.board.size.columns
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
