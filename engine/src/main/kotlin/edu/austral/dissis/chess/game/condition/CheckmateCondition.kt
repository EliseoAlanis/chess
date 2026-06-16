package edu.austral.dissis.chess.game.condition

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.game.Game
import edu.austral.dissis.chess.game.GameStatus
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.chess.rules.basic.ClearPathRule

class CheckmateCondition : WinCondition {
    private val clearPathRule = ClearPathRule()

    override fun checkStatus(game: Game): GameStatus? {
        val currentTurn = game.turnManager.currentTurn
        
        val hasValidMove = hasAnyValidMove(game, currentTurn)
        
        if (hasValidMove) return null // Game continues
        
        val isKingAttacked = isKingAttacked(game.board, currentTurn)
        
        if (isKingAttacked) {
            return if (currentTurn == Color.BLANCO) GameStatus.BLACK_WINS else GameStatus.WHITE_WINS
        } else {
            return GameStatus.DRAW // Stalemate
        }
    }
    
    private fun hasAnyValidMove(game: Game, color: Color): Boolean {
        val myPiecesPositions = game.board.positions.filterValues { it.color == color }.keys
        
        for (origin in myPiecesPositions) {
            for (row in 0 until game.board.size.rows) {
                for (col in 0 until game.board.size.columns) {
                    val destination = Position(row, col)
                    if (game.moveValidator.canMove(game.board, color, origin, destination, game.moveHistory)) {
                        return true
                    }
                }
            }
        }
        return false
    }
    
    private fun isKingAttacked(board: Board, myColor: Color): Boolean {
        val myKingPos = findKingPosition(board, myColor) ?: return false
        val enemyPositions = board.positions.filterValues { it.color != myColor }

        for ((enemyPos, enemyPiece) in enemyPositions) {
            if (enemyPiece.isValidMovePattern(enemyPos, myKingPos, isCapture = true)) {
                if (clearPathRule.isValid(board, enemyPos, myKingPos, enemyPiece.color)) {
                    return true
                }
            }
        }
        return false
    }
    
    private fun findKingPosition(board: Board, color: Color): Position? {
        return board.positions.entries.find { (_, piece) ->
            piece.pieceType == PieceType.KING && piece.color == color
        }?.key
    }
}
