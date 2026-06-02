package edu.austral.dissis.chess.pieces

import edu.austral.dissis.chess.movement.DiagonalMovement
import edu.austral.dissis.chess.movement.LShapeMovement
import edu.austral.dissis.chess.movement.StraightMovement

object PieceFactory {
    fun createRook(
        id: String,
        color: Color,
    ) = Piece(id, color, listOf(StraightMovement()), canJump = false)

    fun createBishop(
        id: String,
        color: Color,
    ) = Piece(id, color, listOf(DiagonalMovement()), canJump = false)

    fun createQueen(
        id: String,
        color: Color,
    ) = Piece(id, color, listOf(StraightMovement(), DiagonalMovement()), canJump = false)

    fun createKnight(
        id: String,
        color: Color,
    ) = Piece(id, color, listOf(LShapeMovement()), canJump = true)
}
