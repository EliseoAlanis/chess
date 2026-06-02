package edu.austral.dissis.chess.pieces

import edu.austral.dissis.chess.movement.Movement
import edu.austral.dissis.chess.movement.Position

data class Piece(
    val id: String,
    val color: Color,
    private val movements: List<Movement>,
    val canJump: Boolean = false,
    val hasMoved: Boolean = false,
) {
    fun isValidMovePattern(
        origin: Position,
        destination: Position,
    ): Boolean =
        movements.any { movement ->
            movement.checkTrajectory(origin, destination)
        }
}
