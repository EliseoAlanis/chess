package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.chess.movement.effect.EnPassantCaptureEffect
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.common.rules.Rule
import kotlin.math.abs

import edu.austral.dissis.common.rules.MoveContext

class EnPassantRule : Rule {
    override fun evaluate(context: MoveContext): RuleResult {
        val board = context.board
        val vector = context.vector
        val moveHistory = context.history
        if (moveHistory.isEmpty() || !isPawnAndEmptyDestination(board, vector)) return RuleResult.Invalid
        
        val piece = board.getPieceAt(vector.origin)
        if (!isValidDiagonalMove(piece, vector)) return RuleResult.Invalid

        val isValid = isValidEnemyPawnMove(moveHistory.last(), piece, vector)

        if (!isValid) return RuleResult.Invalid

        val capturedPawnPosition = Position(vector.origin.row, vector.destination.col)
        return RuleResult.Valid(listOf(EnPassantCaptureEffect(capturedPawnPosition)))
    }

    private fun isPawnAndEmptyDestination(
        board: Board,
        vector: MovementVector,
    ): Boolean {
        val piece = board.getPieceAt(vector.origin)
        return piece.pieceType == PieceType.PAWN && !board.hasPieceAt(vector.destination)
    }

    private fun isValidDiagonalMove(
        piece: Piece,
        vector: MovementVector,
    ): Boolean {
        val direction = if (piece.color == Color.WHITE) 1 else -1
        return vector.destination.row == vector.origin.row + direction && abs(vector.destination.col - vector.origin.col) == 1
    }

    private fun isValidEnemyPawnMove(
        lastMove: MoveRecord,
        ourPiece: Piece,
        vector: MovementVector,
    ): Boolean {
        val lastPiece = lastMove.piece
        val isEnemyPawn = lastPiece.pieceType == PieceType.PAWN && lastPiece.color != ourPiece.color
        val movedTwoSquares = abs(lastMove.destination.row - lastMove.origin.row) == 2
        val landedNextToUs = lastMove.destination.row == vector.origin.row && lastMove.destination.col == vector.destination.col

        return isEnemyPawn && movedTwoSquares && landedNextToUs
    }
}
