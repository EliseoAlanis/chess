package edu.austral.dissis.chess.board

import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Piece

data class Board(
    val positions: Map<Position, Piece> = mapOf(),
    val size: BoardSize,
) {
    fun movePiece(
        origin: Position,
        destination: Position,
    ): Board {
        val pieceToMove = positions[origin] ?: return this

        val nextPositions = positions - origin + (destination to pieceToMove)

        return this.copy(positions = nextPositions)
    }

    fun hasPieceAt(position: Position): Boolean = positions.containsKey(position)

    fun getPieceAt(position: Position): Piece = positions.getValue(position)
}
