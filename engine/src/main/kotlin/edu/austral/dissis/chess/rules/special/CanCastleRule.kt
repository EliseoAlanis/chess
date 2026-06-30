package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.chess.movement.effect.CastlingEffect
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Piece
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.Rule
import edu.austral.dissis.common.rules.RuleResult

class CanCastleRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val board = context.board
        val vector = context.vector
        val moveHistory = context.history
        val piece = board.getPieceAt(vector.origin)

        val isValidKing = isCastlingAttempt(piece, vector) && !hasPieceMoved(piece.id, moveHistory)

        val rookOrigin = getRookPosition(vector, board.size.columns)

        val canCastle = isValidKing && isRookAvailableAndNotMoved(board, rookOrigin, moveHistory)

        if (!canCastle) return RuleResult.Invalid

        val isKingSide = vector.destination.col > vector.origin.col
        val rookDestinationCol = if (isKingSide) vector.destination.col - 1 else vector.destination.col + 1
        val rookDestination = Position(vector.origin.row, rookDestinationCol)

        return RuleResult.Valid(listOf(CastlingEffect(rookOrigin, rookDestination)))
    }

    private fun isCastlingAttempt(
        piece: Piece,
        vector: MovementVector,
    ): Boolean {
        val isKing = piece.pieceType == edu.austral.dissis.chess.pieces.PieceType.KING
        val isHorizontalTwoSteps =
            vector.origin.row == vector.destination.row &&
                Math.abs(vector.destination.col - vector.origin.col) == 2

        return isKing && isHorizontalTwoSteps
    }

    private fun hasPieceMoved(
        pieceId: String,
        moveHistory: List<MoveRecord>,
    ): Boolean = moveHistory.any { it.piece.id == pieceId }

    private fun getRookPosition(
        vector: MovementVector,
        totalColumns: Int,
    ): Position {
        val isKingSide = vector.destination.col > vector.origin.col
        val rookCol = if (isKingSide) totalColumns else 1
        return Position(vector.origin.row, rookCol)
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
