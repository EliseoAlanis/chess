package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.PieceType

class NoSelfCheckRule : BasicRule {
    private val clearPathRule = ClearPathRule()

    override fun isValid(board: Board, origin: Position, destination: Position, currentTurn: Color): Boolean {
        val simulatedBoard = board.movePiece(origin, destination)

        val myKingPos = findKingPosition(simulatedBoard, currentTurn) ?: return true

        return !isSquareAttackedByEnemy(simulatedBoard, myKingPos, currentTurn)
    }

    private fun findKingPosition(board: Board, color: Color): Position? {
        return board.positions.entries.find { (_, piece) ->
            piece.pieceType == PieceType.KING && piece.color == color
        }?.key
    }

    private fun isSquareAttackedByEnemy(board: Board, targetPos: Position, myColor: Color): Boolean {
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
