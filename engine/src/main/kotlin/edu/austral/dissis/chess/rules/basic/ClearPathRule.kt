package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.Rule
import kotlin.math.sign

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class ClearPathRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val piece = context.board.getPieceAt(context.vector.origin)
        val movementRule = piece.rules.filterIsInstance<edu.austral.dissis.common.movement.Movement>().firstOrNull()
        val intermediate = movementRule?.getIntermediatePositions(context.vector) ?: emptyList()
        val isValid = intermediate.none { pos -> context.board.hasPieceAt(pos) }
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
