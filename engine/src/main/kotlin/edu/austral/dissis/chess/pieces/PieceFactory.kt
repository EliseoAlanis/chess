package edu.austral.dissis.chess.pieces

import edu.austral.dissis.chess.movement.Movement
import edu.austral.dissis.chess.movement.atomics.DiagonalMovement
import edu.austral.dissis.chess.movement.atomics.ForwardDiagonalMovement
import edu.austral.dissis.chess.movement.atomics.ForwardMovement
import edu.austral.dissis.chess.movement.atomics.LShapeMovement
import edu.austral.dissis.chess.movement.atomics.StraightMovement
import edu.austral.dissis.chess.movement.composite.CompositeMovement
import edu.austral.dissis.chess.movement.factory.MovementFactory

object PieceFactory {
    fun createRook(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.ROOK, movements = MovementFactory.createRookMovement(), canJump = false)

    fun createBishop(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.BISHOP, movements = MovementFactory.createBishopMovement(), canJump = false)

    fun createQueen(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.QUEEN, movements = MovementFactory.createQueenMovement(), canJump = false)

    fun createKnight(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.KNIGHT, movements = MovementFactory.createKnightMovement(), canJump = true)

    fun createPawn(id: String, color: Color): Piece {
        val direction = if (color == Color.BLANCO) -1 else 1

        return Piece(
            id = id,
            color = color,
            pieceType = PieceType.PAWN,
            movements = ForwardMovement(direction),
            captureMovements = ForwardDiagonalMovement(direction),
            canJump = false
        )
    }

    fun createKing(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.KING, movements = MovementFactory.createKingMovement(), canJump = false)
}
