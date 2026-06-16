package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.MoveRecord
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.PieceType

class PawnInitialDoubleMovementRule : SpecialRule {
    override fun canExecute(
        board: Board,
        origin: Position,
        destination: Position,
        moveHistory: List<MoveRecord>
    ): Boolean {
        val piece = board.getPieceAt(origin)

        if (piece.pieceType != PieceType.PAWN || moveHistory.any { it.piece.id == piece.id }) return false

        val direction = if (piece.color == Color.BLANCO) -1 else 1
        val isDoubleStep = destination.row == origin.row + (2 * direction) && destination.col == origin.col

        if (!isDoubleStep) return false

        val intermediatePos = Position(origin.row + direction, origin.col)
        return !board.hasPieceAt(destination) && !board.hasPieceAt(intermediatePos)
    }
}

