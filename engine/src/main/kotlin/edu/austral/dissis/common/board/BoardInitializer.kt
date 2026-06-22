package edu.austral.dissis.common.board

import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Piece

interface BoardInitializer {
    fun generateInitialPositions(): Map<Position, Piece>
}
