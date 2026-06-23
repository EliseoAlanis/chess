package edu.austral.dissis.common.movement.effect

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path

interface PostMoveEffect {
    fun apply(
        previousBoard: Board,
        currentBoard: Board,
        path: Path,
    ): Board
}
