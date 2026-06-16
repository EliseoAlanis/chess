package edu.austral.dissis.chess.movement.effect

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.PieceType
import kotlin.math.abs

class CastlingEffect : PostMoveEffect {
    override fun apply(
        previousBoard: Board,
        currentBoard: Board,
        origin: Position,
        destination: Position,
    ): Board {
        val piece = currentBoard.getPieceAt(destination)

        if (piece.pieceType != PieceType.KING) return currentBoard

        val isCastling = abs(destination.col - origin.col) == 2

        if (isCastling) {
            val isKingSide = destination.col > origin.col
            val rookOriginCol = if (isKingSide) currentBoard.size.columns - 1 else 0
            val rookDestinationCol = if (isKingSide) destination.col - 1 else destination.col + 1

            val rookOrigin = Position(origin.row, rookOriginCol)
            val rookDestination = Position(origin.row, rookDestinationCol)

            if (currentBoard.hasPieceAt(rookOrigin)) {
                val rook = currentBoard.getPieceAt(rookOrigin)
                val newPositions = currentBoard.positions - rookOrigin + (rookDestination to rook)
                return currentBoard.copy(positions = newPositions)
            }
        }

        return currentBoard
    }
}
