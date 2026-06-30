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
            val path = Path(enemyPos, targetPos)
            val context = edu.austral.dissis.common.rules.MoveContext(board, path, enemyPiece.color)
            if (enemyPiece.evaluateMove(context, isCapture = true) is edu.austral.dissis.common.rules.RuleResult.Valid) {
                if (clearPathRule.evaluate(context) is edu.austral.dissis.common.rules.RuleResult.Valid) {
                    return true
                }
            }
        }
        return false
    }
}
