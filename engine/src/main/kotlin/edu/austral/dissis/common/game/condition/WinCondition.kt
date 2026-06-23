package edu.austral.dissis.common.game.condition

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.game.GameStatus

interface WinCondition {
    fun checkStatus(
        board: Board,
        currentTurn: Color,
        moveHistory: List<MoveRecord>
    ): GameStatus
}
