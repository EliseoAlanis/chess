package edu.austral.dissis.chess.movement.effect

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.chess.pieces.PieceType

class PromotionEffect : PostMoveEffect {
    override fun apply(previousBoard: Board, currentBoard: Board, origin: Position, destination: Position): Board {
        if (!currentBoard.hasPieceAt(destination)) return currentBoard
        
        val piece = currentBoard.getPieceAt(destination)
        if (piece.pieceType != PieceType.PAWN) return currentBoard

        // Check if pawn reached the end of the board
        val promotionRow = if (piece.color == Color.BLANCO) 0 else currentBoard.size.rows - 1
        
        if (destination.row == promotionRow) {
            val promotedPiece = PieceFactory.createQueen(piece.id, piece.color)
            val newPositions = currentBoard.positions + (destination to promotedPiece)
            return currentBoard.copy(positions = newPositions)
        }
        
        return currentBoard
    }
}
