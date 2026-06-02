package edu.austral.dissis.chess.board

import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Piece

interface BoardInitializer {
    fun generateInitialPositions(): Map<Position, Piece>
}
