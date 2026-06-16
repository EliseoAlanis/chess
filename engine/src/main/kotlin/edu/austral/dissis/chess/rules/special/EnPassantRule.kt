package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.MoveRecord
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Piece
import edu.austral.dissis.chess.pieces.PieceType
import kotlin.math.abs

class EnPassantRule : SpecialRule {
    override fun canExecute(
        board: Board,
        origin: Position,
        destination: Position,
        moveHistory: List<MoveRecord>
    ): Boolean {
        if (moveHistory.isEmpty() || !isPawnAndEmptyDestination(board, origin, destination)) return false
        
        val piece = board.getPieceAt(origin)
        if (!isValidDiagonalMove(piece, origin, destination)) return false
        
        return isValidEnemyPawnMove(moveHistory.last(), piece, origin, destination)
    }

    private fun isPawnAndEmptyDestination(board: Board, origin: Position, destination: Position): Boolean {
        val piece = board.getPieceAt(origin)
        return piece.pieceType == PieceType.PAWN && !board.hasPieceAt(destination)
    }

    private fun isValidDiagonalMove(piece: Piece, origin: Position, destination: Position): Boolean {
        val direction = if (piece.color == Color.BLANCO) -1 else 1
        return destination.row == origin.row + direction && abs(destination.col - origin.col) == 1
    }

    private fun isValidEnemyPawnMove(lastMove: MoveRecord, ourPiece: Piece, origin: Position, destination: Position): Boolean {
        val lastPiece = lastMove.piece
        val isEnemyPawn = lastPiece.pieceType == PieceType.PAWN && lastPiece.color != ourPiece.color
        val movedTwoSquares = abs(lastMove.destination.row - lastMove.origin.row) == 2
        val landedNextToUs = lastMove.destination.row == origin.row && lastMove.destination.col == destination.col
        
        return isEnemyPawn && movedTwoSquares && landedNextToUs
    }
}
