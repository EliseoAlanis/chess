package edu.austral.dissis.checkers.game.condition

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.game.GameStatus
import edu.austral.dissis.common.game.condition.WinCondition
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.pieces.Color

class CheckersExtinctionCondition : WinCondition {
    override fun checkStatus(
        board: Board,
        currentTurn: Color,
        moveHistory: List<MoveRecord>
    ): GameStatus {
        val alivePiecesCount = board.positions.values.count { it.color == currentTurn }

        if (alivePiecesCount == 0) {
            return if (currentTurn == Color.WHITE) GameStatus.BLACK_WINS else GameStatus.WHITE_WINS
        }

        return GameStatus.IN_PROGRESS
    }
}
