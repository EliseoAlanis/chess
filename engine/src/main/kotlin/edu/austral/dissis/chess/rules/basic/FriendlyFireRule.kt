package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.Rule

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class FriendlyFireRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        if (!context.board.hasPieceAt(context.vector.destination)) return RuleResult.Valid()
        val isValid = context.board.getPieceAt(context.vector.destination).color != context.turn
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
