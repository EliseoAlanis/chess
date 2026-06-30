package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class BoardLimitsRule : BasicRule {
    override fun evaluate(context: MoveContext): RuleResult {
        val isValid = context.path.destination.row in 1..context.board.size.rows &&
                      context.path.destination.col in 1..context.board.size.columns
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
