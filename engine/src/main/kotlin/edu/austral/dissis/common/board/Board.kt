package edu.austral.dissis.common.board

import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Piece

data class Board(
    private val positions: Map<Position, Piece> = mapOf(),
    private val size: BoardSize,
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

    fun getPositions(): Map<Position, Piece> = positions

    fun getSize(): BoardSize = size
}
