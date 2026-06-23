package edu.austral.dissis.chess.game.condition

import edu.austral.dissis.common.game.condition.WinCondition

import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.GameStatus
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.validator.AttackDetector
import edu.austral.dissis.common.pieces.Color

import edu.austral.dissis.common.movement.validator.MoveValidator

class CheckmateCondition(
    private val moveValidator: MoveValidator,
    private val attackDetector: AttackDetector
) : WinCondition {

    override fun checkStatus(game: Game): GameStatus {
        val currentTurn = game.turnManager.currentTurn

        if (hasAnyValidMove(game, currentTurn)) return GameStatus.IN_PROGRESS

        val isKingAttacked = attackDetector.isKingAttacked(game.board, currentTurn)

        return if (isKingAttacked) {
            if (currentTurn == Color.WHITE) GameStatus.BLACK_WINS else GameStatus.WHITE_WINS
        } else {
            GameStatus.DRAW
        }
    }

    private fun hasAnyValidMove(game: Game, color: Color): Boolean {
        val myPiecesPositions = game.board.positions.filterValues { it.color == color }.keys
        return myPiecesPositions.any { origin -> canPieceMoveToAnySquare(game, color, origin) }
    }

    private fun canPieceMoveToAnySquare(game: Game, color: Color, origin: Position): Boolean {
        val size = game.board.size
        for (row in 1..size.rows) {
            for (col in 1..size.columns) {
                if (isMoveValidAndSafe(game, color, Path(origin, Position(row, col)))) return true
            }
        }
        return false
    }

    private fun isMoveValidAndSafe(game: Game, color: Color, path: Path): Boolean {
        return moveValidator.canMove(game.board, color, path, game.moveHistory)
    }
}
