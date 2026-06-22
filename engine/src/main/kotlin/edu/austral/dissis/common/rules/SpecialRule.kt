package edu.austral.dissis.common.rules

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color

interface SpecialRule {
    fun canExecute(
        board: Board,
        origin: Position,
        destination: Position,
        moveHistory: List<MoveRecord>,
    ): Boolean
}
