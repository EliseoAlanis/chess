package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.Rule

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class CorrectTurnRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val pieceToMove = context.board.getPieceAt(context.vector.origin)
        val isValid = pieceToMove.color == context.turn
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
