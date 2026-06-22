package edu.austral.dissis.common.movement.validator

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.RuleResult

interface MoveValidator {
    fun validateMove(
        board: Board,
        turn: Color,
        origin: Position,
        destination: Position,
        history: List<MoveRecord>,
    ): RuleResult

    fun canMove(
        board: Board,
        turn: Color,
        origin: Position,
        destination: Position,
        history: List<MoveRecord>,
    ): Boolean
}
