package edu.austral.dissis.chess.movement.effect

import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.chess.pieces.PieceType

import edu.austral.dissis.common.movement.effect.PostMoveEffect

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color

class PromotionEffect : PostMoveEffect {
    override fun apply(
        previousBoard: Board,
        currentBoard: Board,
        origin: Position,
        destination: Position,
    ): Board {
        if (!currentBoard.hasPieceAt(destination)) return currentBoard

        val piece = currentBoard.getPieceAt(destination)
        if (piece.pieceType != PieceType.PAWN) return currentBoard

        val promotionRow = if (piece.color == Color.WHITE) currentBoard.size.rows else 1

        if (destination.row == promotionRow) {
            val promotedPiece = PieceFactory.createQueen(piece.id, piece.color)
            val newPositions = currentBoard.positions + (destination to promotedPiece)
            return currentBoard.copy(positions = newPositions)
        }

        return currentBoard
    }
}

