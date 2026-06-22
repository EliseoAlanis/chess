package edu.austral.dissis.common.movement.validator

import edu.austral.dissis.chess.pieces.PieceType




import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule
import edu.austral.dissis.chess.rules.basic.ClearPathRule

class AttackDetector (
){
    private val clearPathRule = ClearPathRule()

    fun isKingAttacked(
        board: Board,
        myColor: Color,
    ): Boolean {
        val myKingPos = findKingPosition(board, myColor) ?: return false
        return isSquareAttackedByEnemy(board, myKingPos, myColor)
    }

    fun findKingPosition(
        board: Board,
        color: Color,
    ): Position? =
        board.positions.entries
            .find { (_, piece) ->
                piece.pieceType == PieceType.KING && piece.color == color
            }?.key

    fun isSquareAttackedByEnemy(
        board: Board,
        targetPos: Position,
        myColor: Color,
    ): Boolean {
        val enemyPositions = board.positions.filterValues { it.color != myColor }

        for ((enemyPos, enemyPiece) in enemyPositions) {
            if (enemyPiece.isValidMovePattern(enemyPos, targetPos, isCapture = true)) {
                if (clearPathRule.isValid(board, enemyPos, targetPos, enemyPiece.color)) {
                    return true
                }
            }
        }
        return false
    }
}



