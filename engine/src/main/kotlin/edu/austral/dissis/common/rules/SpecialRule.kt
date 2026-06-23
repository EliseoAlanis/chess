package edu.austral.dissis.common.rules

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Position

interface SpecialRule {
    fun tryExecute(
        board: Board,
        origin: Position,
        destination: Position,
        moveHistory: List<MoveRecord>,
    ): RuleResult
}
