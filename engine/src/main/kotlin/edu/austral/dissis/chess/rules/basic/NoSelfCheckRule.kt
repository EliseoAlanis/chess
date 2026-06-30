package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.movement.validator.AttackDetector
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.Rule
import edu.austral.dissis.common.rules.RuleResult

class NoSelfCheckRule(
    private val attackDetector: AttackDetector,
) : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val simulatedBoard = context.board.movePiece(context.vector)
        val myKingPos = attackDetector.findPiecePosition(simulatedBoard, PieceType.KING, context.turn) ?: return RuleResult.Valid()
        val isValid = !attackDetector.isSquareAttackedByEnemy(simulatedBoard, myKingPos, context.turn)
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
