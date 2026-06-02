package edu.austral.dissis.chess.movement

import edu.austral.dissis.chess.pieces.Piece

data class MoveRecord(
    val piece: Piece,
    val origin: Position,
    val destination: Position,
)
