package edu.austral.dissis.chess.game.condition

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.game.Game
import edu.austral.dissis.chess.game.GameStatus
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.movement.validator.AttackDetector
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.chess.rules.basic.ClearPathRule

import edu.austral.dissis.chess.movement.validator.MoveValidator

class CheckmateCondition(
    private val moveValidator: MoveValidator,
    private val attackDetector: AttackDetector
) : WinCondition {

    override fun checkStatus(game: Game): GameStatus {
        val currentTurn = game.turnManager.currentTurn

        val hasValidMove = hasAnyValidMove(game, currentTurn)

        if (hasValidMove) return GameStatus.IN_PROGRESS

        val isKingAttacked = attackDetector.isKingAttacked(game.board, currentTurn)

        return if (isKingAttacked) {
            if (currentTurn == Color.BLANCO) GameStatus.BLACK_WINS else GameStatus.WHITE_WINS
        } else {
            GameStatus.DRAW
        }
    }

    private fun hasAnyValidMove(
        game: Game,
        color: Color,
    ): Boolean {
        val myPiecesPositions =
            game.board.positions
                .filterValues { it.color == color }
                .keys

        for (origin in myPiecesPositions) {
            for (row in 1..game.board.size.rows) {
                for (col in 1..game.board.size.columns) {
                    val destination = Position(row, col)
                    if (moveValidator.canMove(game.board, color, origin, destination, game.moveHistory)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
