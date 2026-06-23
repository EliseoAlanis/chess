package edu.austral.dissis.common.movement.effect

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position

interface PostMoveEffect {
    fun apply(
        previousBoard: Board,
        currentBoard: Board,
        path: Path,
    ): Board
}
