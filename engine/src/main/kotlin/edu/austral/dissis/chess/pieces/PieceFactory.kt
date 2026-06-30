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
    ): Piece = Piece(id, color, PieceType.ROOK, rules = listOf(MovementFactory.createRookMovement()))

    fun createBishop(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.BISHOP, rules = listOf(MovementFactory.createBishopMovement()))

    fun createQueen(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.QUEEN, rules = listOf(MovementFactory.createQueenMovement()))

    fun createKnight(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.KNIGHT, rules = listOf(MovementFactory.createKnightMovement()))

    fun createPawn(
        id: String,
        color: Color,
    ): Piece {
        val direction = if (color == Color.WHITE) 1 else -1

        return Piece(
            id = id,
            color = color,
            pieceType = PieceType.PAWN,
            rules =
                listOf(
                    edu.austral.dissis.chess.rules.special
                        .PawnInitialDoubleMovementRule(),
                    edu.austral.dissis.chess.rules.special
                        .EnPassantRule(),
                    edu.austral.dissis.common.movement
                        .ClearPathMovement(ForwardMovement(direction)),
                ),
            captureRules =
                listOf(
                    edu.austral.dissis.chess.rules.special
                        .EnPassantRule(),
                    ForwardDiagonalMovement(direction),
                ),
        )
    }

    fun createKing(
        id: String,
        color: Color,
    ): Piece =
        Piece(
            id,
            color,
            PieceType.KING,
            rules =
                listOf(
                    edu.austral.dissis.chess.rules.special
                        .CanCastleRule(),
                    MovementFactory.createKingMovement(),
                ),
        )

    fun createChancellor(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.CHANCELLOR, rules = listOf(MovementFactory.createChancellorMovement()))

    fun createArchbishop(
        id: String,
        color: Color,
    ): Piece = Piece(id, color, PieceType.ARCHBISHOP, rules = listOf(MovementFactory.createArchbishopMovement()))
}
