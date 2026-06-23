package edu.austral.dissis.common.board

import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Piece

data class Board(
    val positions: Map<Position, Piece> = mapOf(),
    val size: BoardSize,
) {
    fun movePiece(path: Path): Board {
        val pieceToMove = positions[path.origin] ?: return this

        val nextPositions = positions - path.origin + (path.destination to pieceToMove)

        return this.copy(positions = nextPositions)
    }

    fun hasPieceAt(position: Position): Boolean = positions.containsKey(position)

    fun getPieceAt(position: Position): Piece = positions.getValue(position)
}
