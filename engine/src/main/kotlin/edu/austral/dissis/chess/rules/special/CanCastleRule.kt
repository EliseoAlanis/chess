package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.MoveRecord
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Piece

class CanCastleRule : SpecialRule {
    override fun canExecute(
        board: Board,
        origin: Position,
        destination: Position,
        moveHistory: List<MoveRecord>,
    ): Boolean {
        val piece = board.getPieceAt(origin)

        val isValidKing = isCastlingAttempt(piece, origin, destination) && !hasPieceMoved(piece.id, moveHistory)

        return isValidKing &&
            isRookAvailableAndNotMoved(
                board,
                getRookPosition(origin, destination, board.size.columns),
                moveHistory,
            )
    }

    private fun isCastlingAttempt(
        piece: Piece,
        origin: Position,
        destination: Position,
    ): Boolean {
        val isKing = piece.id.lowercase().contains("king")
        val isHorizontalTwoSteps = origin.row == destination.row && Math.abs(destination.col - origin.col) == 2

        return isKing && isHorizontalTwoSteps
    }

    private fun hasPieceMoved(
        pieceId: String,
        moveHistory: List<MoveRecord>,
    ): Boolean = moveHistory.any { it.piece.id == pieceId }

    // un problema que si se mueve la torre por otro regla no funca
    private fun getRookPosition(
        origin: Position,
        destination: Position,
        totalColumns: Int,
    ): Position {
        val isKingSide = destination.col > origin.col
        val rookCol = if (isKingSide) totalColumns - 1 else 0
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
