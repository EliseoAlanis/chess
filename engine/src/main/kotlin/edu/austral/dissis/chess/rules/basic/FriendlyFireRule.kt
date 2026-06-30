package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class FriendlyFireRule : BasicRule {
    override fun evaluate(context: MoveContext): RuleResult {
        if (!context.board.hasPieceAt(context.path.destination)) return RuleResult.Valid()
        val isValid = context.board.getPieceAt(context.path.destination).color != context.turn
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
