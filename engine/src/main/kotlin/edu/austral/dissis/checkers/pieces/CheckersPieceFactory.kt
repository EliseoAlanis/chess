package edu.austral.dissis.checkers.pieces

import edu.austral.dissis.checkers.movement.atomics.CheckersDiagonalMovement
import edu.austral.dissis.checkers.movement.atomics.CheckersForwardDiagonalMovement
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece

object CheckersPieceFactory {
    fun createMan(
        id: String,
        color: Color,
    ): Piece {
        val direction = if (color == Color.WHITE) 1 else -1

        return Piece(
            id = id,
            color = color,
            pieceType = CheckersPieceTypes.MAN,
            rules = listOf(CheckersForwardDiagonalMovement(direction, maxSteps = 1)),
            captureRules = listOf(CheckersForwardDiagonalMovement(direction, maxSteps = 2)),
        )
    }

    fun createKing(
        id: String,
        color: Color,
    ): Piece =
        Piece(
            id = id,
            color = color,
            pieceType = CheckersPieceTypes.KING,
            rules = listOf(CheckersDiagonalMovement(maxSteps = 1)),
            captureRules = listOf(CheckersDiagonalMovement(maxSteps = 2)),
        )
}
