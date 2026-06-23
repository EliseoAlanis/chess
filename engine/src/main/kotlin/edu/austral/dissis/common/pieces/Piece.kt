package edu.austral.dissis.common.pieces


import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Position

data class Piece(
    private val id: String,
    private val color: Color,
    private val pieceType: String,
    private val movements: Movement,
    private val captureMovements: Movement = movements,
    private val canJump: Boolean = false, //mandar a la mierda
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
