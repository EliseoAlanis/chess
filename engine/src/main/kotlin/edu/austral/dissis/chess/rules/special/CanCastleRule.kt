package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.common.rules.SpecialRule
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.chess.movement.effect.CastlingEffect
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Piece

class CanCastleRule : SpecialRule {
    override fun tryExecute(
        board: Board,
        origin: Position,
        destination: Position,
        moveHistory: List<MoveRecord>,
    ): RuleResult {
        val piece = board.getPieceAt(origin)

        val isValidKing = isCastlingAttempt(piece, origin, destination) && !hasPieceMoved(piece.id, moveHistory)

        val rookOrigin = getRookPosition(origin, destination, board.size.columns)
        
        val canCastle = isValidKing && isRookAvailableAndNotMoved(board, rookOrigin, moveHistory)

        if (!canCastle) return RuleResult.Invalid

        val isKingSide = destination.col > origin.col
        val rookDestinationCol = if (isKingSide) destination.col - 1 else destination.col + 1
        val rookDestination = Position(origin.row, rookDestinationCol)

        return RuleResult.Valid(listOf(CastlingEffect(rookOrigin, rookDestination)))
    }

    private fun isCastlingAttempt(
        piece: Piece,
        origin: Position,
        destination: Position,
    ): Boolean {
        val isKing = piece.pieceType == edu.austral.dissis.chess.pieces.PieceType.KING
        val isHorizontalTwoSteps = origin.row == destination.row && Math.abs(destination.col - origin.col) == 2

        return isKing && isHorizontalTwoSteps
    }

    private fun hasPieceMoved(
        pieceId: String,
        moveHistory: List<MoveRecord>,
    ): Boolean = moveHistory.any { it.piece.id == pieceId }

    private fun getRookPosition(
        origin: Position,
        destination: Position,
        totalColumns: Int,
    ): Position {
        val isKingSide = destination.col > origin.col
        val rookCol = if (isKingSide) totalColumns else 1
        return Position(origin.row, rookCol)
    }

    private fun isRookAvailableAndNotMoved(
        board: Board,
        rookPosition: Position,
        moveHistory: List<MoveRecord>,
    ): Boolean {
        if (!board.hasPieceAt(rookPosition)) return false
        val rook = board.getPieceAt(rookPosition)
        return !hasPieceMoved(rook.id, moveHistory)
    }
}
