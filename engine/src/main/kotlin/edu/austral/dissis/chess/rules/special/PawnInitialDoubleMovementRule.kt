package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.common.rules.SpecialRule
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color

class PawnInitialDoubleMovementRule : SpecialRule {
    override fun tryExecute(
        board: Board,
        origin: Position,
        destination: Position,
        moveHistory: List<MoveRecord>,
    ): RuleResult {
        val piece = board.getPieceAt(origin)

        if (piece.pieceType != PieceType.PAWN || moveHistory.any { it.piece.id == piece.id }) return RuleResult.Invalid

        val direction = if (piece.color == Color.WHITE) 1 else -1
        val isDoubleStep = destination.row == origin.row + (2 * direction) && destination.col == origin.col

        if (!isDoubleStep) return RuleResult.Invalid

        val intermediatePos = Position(origin.row + direction, origin.col)
        
        val canMove = !board.hasPieceAt(destination) && !board.hasPieceAt(intermediatePos)
        return if (canMove) RuleResult.Valid() else RuleResult.Invalid
    }
}
