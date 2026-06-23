package edu.austral.dissis.common.rules

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color

interface BasicRule {
    fun isValid(
        board: Board,
        path: Path,
        currentTurn: Color,
    ): Boolean
}
