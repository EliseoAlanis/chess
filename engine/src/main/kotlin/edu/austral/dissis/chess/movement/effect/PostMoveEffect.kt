package edu.austral.dissis.chess.movement.effect

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position

interface PostMoveEffect {
    fun apply(
        previousBoard: Board,
        currentBoard: Board,
        origin: Position,
        destination: Position,
    ): Board
}
