package edu.austral.dissis.chess.pieces

import edu.austral.dissis.chess.movement.atomics.ForwardDiagonalMovement
import edu.austral.dissis.chess.movement.atomics.ForwardMovement
import edu.austral.dissis.chess.movement.factory.MovementFactory
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece

object PieceFactory {
    fun createRook(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.ROOK, movements = MovementFactory.createRookMovement())

    fun createBishop(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.BISHOP, movements = MovementFactory.createBishopMovement())

    fun createQueen(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.QUEEN, movements = MovementFactory.createQueenMovement())

    fun createKnight(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.KNIGHT, movements = MovementFactory.createKnightMovement())

    fun createPawn(
        id: String,
        color: Color,
    ): Piece {
        val direction = if (color == Color.WHITE) 1 else -1

        return Piece(
            id = id,
            color = color,
            pieceType = PieceType.PAWN,
            movements = ForwardMovement(direction),
            captureMovements = ForwardDiagonalMovement(direction),
        )
    }

    fun createKing(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.KING, movements = MovementFactory.createKingMovement())

    fun createChancellor(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.CHANCELLOR, movements = MovementFactory.createChancellorMovement())

    fun createArchbishop(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.ARCHBISHOP, movements = MovementFactory.createArchbishopMovement())
}
