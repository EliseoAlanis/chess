package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.common.rules.SpecialRule
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.chess.movement.effect.EnPassantCaptureEffect

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import kotlin.math.abs

class EnPassantRule : SpecialRule {
    override fun tryExecute(
        board: Board,
        path: Path,
        moveHistory: List<MoveRecord>,
    ): RuleResult {
        if (moveHistory.isEmpty() || !isPawnAndEmptyDestination(board, path)) return RuleResult.Invalid

        val piece = board.getPieceAt(path.origin)
        if (!isValidDiagonalMove(piece, path)) return RuleResult.Invalid

        val isValid = isValidEnemyPawnMove(moveHistory.last(), piece, path)
        
        if (!isValid) return RuleResult.Invalid
        
        val capturedPawnPosition = Position(path.origin.row, path.destination.col)
        return RuleResult.Valid(listOf(EnPassantCaptureEffect(capturedPawnPosition)))
    }

    private fun isPawnAndEmptyDestination(
        board: Board,
        path: Path,
    ): Boolean {
        val piece = board.getPieceAt(path.origin)
        return piece.pieceType == PieceType.PAWN && !board.hasPieceAt(path.destination)
    }

    private fun isValidDiagonalMove(
        piece: Piece,
        path: Path,
    ): Boolean {
        val direction = if (piece.color == Color.WHITE) 1 else -1
        return path.destination.row == path.origin.row + direction && abs(path.destination.col - path.origin.col) == 1
    }

    private fun isValidEnemyPawnMove(
        lastMove: MoveRecord,
        ourPiece: Piece,
        path: Path,
    ): Boolean {
        val lastPiece = lastMove.piece
        val isEnemyPawn = lastPiece.pieceType == PieceType.PAWN && lastPiece.color != ourPiece.color
        val movedTwoSquares = abs(lastMove.destination.row - lastMove.origin.row) == 2
        val landedNextToUs = lastMove.destination.row == path.origin.row && lastMove.destination.col == path.destination.col

        return isEnemyPawn && movedTwoSquares && landedNextToUs
    }
}
