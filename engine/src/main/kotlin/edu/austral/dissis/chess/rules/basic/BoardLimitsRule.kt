package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.rules.BasicRule


import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color

class BoardLimitsRule : BasicRule {
    override fun isValid(
        board: Board,
        path: Path,
        currentTurn: Color,
    ): Boolean =
        path.destination.row in 1..board.size.rows &&
            path.destination.col in 1..board.size.columns
}


