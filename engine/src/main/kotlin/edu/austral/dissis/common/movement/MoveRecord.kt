package edu.austral.dissis.common.movement

import edu.austral.dissis.common.pieces.Piece

data class MoveRecord(
    val piece: Piece,
    val origin: Position,
    val destination: Position,
)
