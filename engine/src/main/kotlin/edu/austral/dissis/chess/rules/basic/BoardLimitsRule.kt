package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.rules.BasicRule


import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color

class BoardLimitsRule : BasicRule {
    override fun isValid(
        board: Board,
        origin: Position,
        destination: Position,
        currentTurn: Color,
    ): Boolean =
        destination.row in 1..board.size.rows &&
            destination.col in 1..board.size.columns
}


