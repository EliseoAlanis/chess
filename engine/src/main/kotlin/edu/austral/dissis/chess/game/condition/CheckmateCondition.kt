package edu.austral.dissis.chess.game.condition

import edu.austral.dissis.chess.movement.validator.AttackDetector
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.game.GameStatus
import edu.austral.dissis.common.game.condition.WinCondition
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.pieces.Color

class CheckmateCondition(
    private val moveValidator: MoveValidator,
    private val attackDetector: AttackDetector,
) : WinCondition {
    override fun checkStatus(
        board: Board,
        currentTurn: Color,
        moveHistory: List<MoveRecord>,
    ): GameStatus {
        if (hasAnyValidMove(board, currentTurn, moveHistory)) return GameStatus.IN_PROGRESS

        val isKingAttacked = attackDetector.isKingAttacked(board, currentTurn)

        return if (isKingAttacked) {
            if (currentTurn == Color.WHITE) GameStatus.BLACK_WINS else GameStatus.WHITE_WINS
        } else {
            GameStatus.DRAW
        }
    }

    private fun hasAnyValidMove(
        board: Board,
        color: Color,
        moveHistory: List<MoveRecord>,
    ): Boolean {
        val myPiecesPositions = board.positions.filterValues { it.color == color }.keys
        return myPiecesPositions.any { origin -> canPieceMoveToAnySquare(board, color, origin, moveHistory) }
    }

    private fun canPieceMoveToAnySquare(
        board: Board,
        color: Color,
        origin: Position,
        moveHistory: List<MoveRecord>,
    ): Boolean {
        val size = board.size
        for (row in 1..size.rows) {
            for (col in 1..size.columns) {
                if (isMoveValidAndSafe(board, color, Path(origin, Position(row, col)), moveHistory)) return true
            }
        }
        return false
    }

    private fun isMoveValidAndSafe(
        board: Board,
        color: Color,
        path: Path,
        moveHistory: List<MoveRecord>,
    ): Boolean = moveValidator.canMove(board, color, path, moveHistory)
}
