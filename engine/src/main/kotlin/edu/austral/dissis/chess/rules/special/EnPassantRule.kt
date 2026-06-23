package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.common.rules.SpecialRule
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.chess.movement.effect.EnPassantCaptureEffect

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import kotlin.math.abs

class EnPassantRule : SpecialRule {
    override fun tryExecute(
        board: Board,
        origin: Position,
        destination: Position,
        moveHistory: List<MoveRecord>,
    ): RuleResult {
        if (moveHistory.isEmpty() || !isPawnAndEmptyDestination(board, origin, destination)) return RuleResult.Invalid

        val piece = board.getPieceAt(origin)
        if (!isValidDiagonalMove(piece, origin, destination)) return RuleResult.Invalid

        val isValid = isValidEnemyPawnMove(moveHistory.last(), piece, origin, destination)
        
        if (!isValid) return RuleResult.Invalid
        
        val capturedPawnPosition = Position(origin.row, destination.col)
        return RuleResult.Valid(listOf(EnPassantCaptureEffect(capturedPawnPosition)))
    }

    private fun isPawnAndEmptyDestination(
        board: Board,
        origin: Position,
        destination: Position,
    ): Boolean {
        val piece = board.getPieceAt(origin)
        return piece.pieceType == PieceType.PAWN && !board.hasPieceAt(destination)
    }

    private fun isValidDiagonalMove(
        piece: Piece,
        origin: Position,
        destination: Position,
    ): Boolean {
        val direction = if (piece.color == Color.WHITE) 1 else -1
        return destination.row == origin.row + direction && abs(destination.col - origin.col) == 1
    }

    private fun isValidEnemyPawnMove(
        lastMove: MoveRecord,
        ourPiece: Piece,
        origin: Position,
        destination: Position,
    ): Boolean {
        val lastPiece = lastMove.piece
        val isEnemyPawn = lastPiece.pieceType == PieceType.PAWN && lastPiece.color != ourPiece.color
        val movedTwoSquares = abs(lastMove.destination.row - lastMove.origin.row) == 2
        val landedNextToUs = lastMove.destination.row == origin.row && lastMove.destination.col == destination.col

        return isEnemyPawn && movedTwoSquares && landedNextToUs
    }
}
