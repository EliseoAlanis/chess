package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.common.rules.Rule

import edu.austral.dissis.common.rules.MoveContext

class PawnInitialDoubleMovementRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val board = context.board
        val path = context.path
        val moveHistory = context.history
        
        val piece = board.getPieceAt(path.origin)

        if (piece.pieceType != PieceType.PAWN || moveHistory.any { it.piece.id == piece.id }) return RuleResult.Invalid

        val direction = if (piece.color == Color.WHITE) 1 else -1
        val isDoubleStep = path.destination.row == path.origin.row + (2 * direction) && path.destination.col == path.origin.col

        if (!isDoubleStep) return RuleResult.Invalid

        val intermediatePos = Position(path.origin.row + direction, path.origin.col)

        val canMove = !board.hasPieceAt(path.destination) && !board.hasPieceAt(intermediatePos)
        return if (canMove) RuleResult.Valid() else RuleResult.Invalid
    }
}
