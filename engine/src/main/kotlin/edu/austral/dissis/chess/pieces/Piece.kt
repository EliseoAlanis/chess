package edu.austral.dissis.chess.pieces

import edu.austral.dissis.chess.movement.Movement
import edu.austral.dissis.chess.movement.Position

data class Piece(
    val id: String,
    val color: Color,
    val pieceType: PieceType,
    private val movements: Movement,
    private val captureMovements: Movement = movements,
    val canJump: Boolean = false,
) {
    fun isValidMovePattern(
        origin: Position,
        destination: Position,
        isCapture: Boolean = false,
    ): Boolean {
        val movementToUse = if (isCapture) captureMovements else movements
        return movementToUse.checkTrajectory(origin, destination)
    }
}
