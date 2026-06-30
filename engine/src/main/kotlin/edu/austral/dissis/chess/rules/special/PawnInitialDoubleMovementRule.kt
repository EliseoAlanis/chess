package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.common.rules.Rule

import edu.austral.dissis.common.rules.MoveContext

class PawnInitialDoubleMovementRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val board = context.board
        val vector = context.vector
        val moveHistory = context.history
        
        val piece = board.getPieceAt(vector.origin)

        if (piece.pieceType != PieceType.PAWN || moveHistory.any { it.piece.id == piece.id }) return RuleResult.Invalid

        val direction = if (piece.color == Color.WHITE) 1 else -1
        val isDoubleStep = vector.destination.row == vector.origin.row + (2 * direction) && vector.destination.col == vector.origin.col

        if (!isDoubleStep) return RuleResult.Invalid

        val intermediatePos = Position(vector.origin.row + direction, vector.origin.col)

        val canMove = !board.hasPieceAt(vector.destination) && !board.hasPieceAt(intermediatePos)
        return if (canMove) RuleResult.Valid() else RuleResult.Invalid
    }
}
