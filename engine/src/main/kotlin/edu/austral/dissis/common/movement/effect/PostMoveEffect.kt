package edu.austral.dissis.common.movement.effect

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position

interface PostMoveEffect {
    fun apply(
        previousBoard: Board,
        currentBoard: Board,
        origin: Position,
        destination: Position,
    ): Board
}
