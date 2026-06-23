package edu.austral.dissis.chess.movement.validator

import edu.austral.dissis.chess.rules.basic.ClearPathRule
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color

class AttackDetector {
    private val clearPathRule = ClearPathRule()

    fun isPieceAttacked(
        board: Board,
        pieceType: String,
        myColor: Color,
    ): Boolean {
        val piecePos = findPiecePosition(board, pieceType, myColor) ?: return false
        return isSquareAttackedByEnemy(board, piecePos, myColor)
    }

    fun findPiecePosition(
        board: Board,
        pieceType: String,
        color: Color,
    ): Position? =
        board.positions.entries
            .find { (_, piece) ->
                piece.pieceType == pieceType && piece.color == color
            }?.key

    fun isSquareAttackedByEnemy(
        board: Board,
        targetPos: Position,
        myColor: Color,
    ): Boolean {
        val enemyPositions = board.positions.filterValues { it.color != myColor }

        for ((enemyPos, enemyPiece) in enemyPositions) {
            if (enemyPiece.isValidMovePattern(Path(enemyPos, targetPos), isCapture = true)) {
                if (clearPathRule.isValid(board, Path(enemyPos, targetPos), myColor)) {
                    return true
                }
            }
        }
        return false
    }
}
