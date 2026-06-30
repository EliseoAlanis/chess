package edu.austral.dissis.common.movement.validator

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.RuleResult

interface MoveValidator {
    fun validateMove(
        board: Board,
        turn: Color,
        history: List<MoveRecord>,
        vector: MovementVector,
    ): RuleResult

    fun canMove(
        board: Board,
        turn: Color,
        vector: MovementVector,
        history: List<MoveRecord>,
    ): Boolean
}
