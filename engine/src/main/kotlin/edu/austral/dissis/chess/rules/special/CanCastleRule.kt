package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.common.rules.SpecialRule
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.chess.movement.effect.CastlingEffect
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Piece

class CanCastleRule : SpecialRule {
    override fun tryExecute(
        board: Board,
        path: Path,
        moveHistory: List<MoveRecord>,
    ): RuleResult {
        val piece = board.getPieceAt(path.origin)

        val isValidKing = isCastlingAttempt(piece, path) && !hasPieceMoved(piece.id, moveHistory)

        val rookOrigin = getRookPosition(path, board.size.columns)
        
        val canCastle = isValidKing && isRookAvailableAndNotMoved(board, rookOrigin, moveHistory)

        if (!canCastle) return RuleResult.Invalid

        val isKingSide = path.destination.col > path.origin.col
        val rookDestinationCol = if (isKingSide) path.destination.col - 1 else path.destination.col + 1
        val rookDestination = Position(path.origin.row, rookDestinationCol)

        return RuleResult.Valid(listOf(CastlingEffect(rookOrigin, rookDestination)))
    }

    private fun isCastlingAttempt(
        piece: Piece,
        path: Path,
    ): Boolean {
        val isKing = piece.pieceType == edu.austral.dissis.chess.pieces.PieceType.KING
        val isHorizontalTwoSteps = path.origin.row == path.destination.row && Math.abs(path.destination.col - path.origin.col) == 2

        return isKing && isHorizontalTwoSteps
    }

    private fun hasPieceMoved(
        pieceId: String,
        moveHistory: List<MoveRecord>,
    ): Boolean = moveHistory.any { it.piece.id == pieceId }

    private fun getRookPosition(
        path: Path,
        totalColumns: Int,
    ): Position {
        val isKingSide = path.destination.col > path.origin.col
        val rookCol = if (isKingSide) totalColumns else 1
        return Position(path.origin.row, rookCol)
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
