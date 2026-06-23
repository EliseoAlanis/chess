package edu.austral.dissis.common.pieces


import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path

data class Piece(
    val id: String,
    val color: Color,
    val pieceType: String,
    val movements: Movement,
    val captureMovements: Movement = movements,
    val canJump: Boolean = false, //mandar a la mierda
) {
    fun isValidMovePattern(
        path: Path,
        isCapture: Boolean = false,
    ): Boolean {
        val movementToUse = if (isCapture) captureMovements else movements
        return movementToUse.checkTrajectory(path)
    }
}
