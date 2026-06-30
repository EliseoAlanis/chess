package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule
import kotlin.math.sign

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class ClearPathRule : BasicRule {
    override fun evaluate(context: MoveContext): RuleResult {
        val piece = context.board.getPieceAt(context.path.origin)
        val intermediate = piece.movements.getIntermediatePositions(context.path)
        val isValid = intermediate.none { pos -> context.board.hasPieceAt(pos) }
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
