package edu.austral.dissis.chess.pieces

import edu.austral.dissis.chess.movement.Position

interface Piece {
    val color: Color

    fun isValidMovePattern(
        origin: Position,
        destination: Position,
    ): Boolean
}
