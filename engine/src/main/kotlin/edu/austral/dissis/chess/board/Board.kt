package edu.austral.dissis.chess.board

import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Piece

interface Board {
    fun movePiece(
        origin: Position,
        destination: Position,
    ): NormalBoard

    fun hasPieceAt(position: Position): Boolean

    fun getPieceAt(position: Position): Piece
}
