package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class CorrectTurnRule : BasicRule {
    override fun evaluate(context: MoveContext): RuleResult {
        val pieceToMove = context.board.getPieceAt(context.path.origin)
        val isValid = pieceToMove.color == context.turn
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
