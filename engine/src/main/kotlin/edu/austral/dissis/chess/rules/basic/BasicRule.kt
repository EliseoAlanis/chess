package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color

interface BasicRule {
    fun isValid(
        board: Board,
        origin: Position,
        destination: Position,
        currentTurn: Color,
    ): Boolean
}
