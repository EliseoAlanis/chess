package edu.austral.dissis.checkers.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.Rule
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class CorrectTurnRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val pieceToMove = context.board.getPieceAt(context.path.origin)
        val isValid = pieceToMove.color == context.turn
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}

class BoardLimitsRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val isValid = context.path.destination.row in 1..context.board.size.rows &&
                      context.path.destination.col in 1..context.board.size.columns
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}

class EmptyDestinationRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val isValid = !context.board.hasPieceAt(context.path.destination)
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
