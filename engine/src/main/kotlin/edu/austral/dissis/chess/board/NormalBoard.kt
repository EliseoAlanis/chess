package edu.austral.dissis.chess.board

import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Piece

data class NormalBoard(
    val positions: Map<Position, Piece> = mapOf(),
) : Board {
    override fun movePiece(
        origin: Position,
        destination: Position,
    ): NormalBoard {
        val pieceToMove = positions[origin] ?: return this

        val nextPositions = positions - origin + (destination to pieceToMove)

        return this.copy(positions = nextPositions)
    }

    override fun hasPieceAt(position: Position): Boolean = positions.containsKey(position)

    override fun getPieceAt(position: Position): Piece = positions.getValue(position)
}
