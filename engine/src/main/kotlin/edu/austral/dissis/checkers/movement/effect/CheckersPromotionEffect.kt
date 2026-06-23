package edu.austral.dissis.checkers.movement.effect

import edu.austral.dissis.checkers.pieces.CheckersPieceFactory
import edu.austral.dissis.checkers.pieces.CheckersPieceTypes
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.effect.PostMoveEffect
import edu.austral.dissis.common.pieces.Color

class CheckersPromotionEffect : PostMoveEffect {
    override fun apply(
        previousBoard: Board,
        currentBoard: Board,
        path: Path,
    ): Board {
        val piece = currentBoard.getPieceAt(path.destination)
        var resultBoard = currentBoard

        if (piece.pieceType == CheckersPieceTypes.MAN) {
            val promotionRow = if (piece.color == Color.WHITE) currentBoard.size.rows else 1
            if (path.destination.row == promotionRow) {
                val king = CheckersPieceFactory.createKing(piece.id, piece.color)
                val newPositions = currentBoard.positions.toMutableMap()
                newPositions[path.destination] = king
                resultBoard = currentBoard.copy(positions = newPositions)
            }
        }

        return resultBoard
    }
}
