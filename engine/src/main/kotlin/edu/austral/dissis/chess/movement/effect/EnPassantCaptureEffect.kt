package edu.austral.dissis.chess.movement.effect

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.PieceType

class EnPassantCaptureEffect : PostMoveEffect {
    override fun apply(
        previousBoard: Board,
        currentBoard: Board,
        origin: Position,
        destination: Position,
    ): Board {
        val piece = currentBoard.getPieceAt(destination)

        if (piece.pieceType != PieceType.PAWN) return currentBoard

        val isDiagonalMove = origin.col != destination.col
        val destinationWasEmpty = !previousBoard.hasPieceAt(destination)

        if (isDiagonalMove && destinationWasEmpty) {
            val capturedPawnPosition = Position(origin.row, destination.col)
            val newPositions = currentBoard.positions - capturedPawnPosition
            return currentBoard.copy(positions = newPositions)
        }

        return currentBoard
    }
}
