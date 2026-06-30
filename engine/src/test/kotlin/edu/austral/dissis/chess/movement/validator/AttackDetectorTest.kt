package edu.austral.dissis.chess.movement.validator

import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AttackDetectorTest {
    private fun createBoard(positions: Map<Position, Piece>): Board = Board(positions, BoardSize(8, 8))

    @Test
    fun `isSquareAttackedByEnemy returns true if a square is attacked`() {
        val attackDetector = AttackDetector()

        // Setup: A white king at 1,1 and a black rook at 1,8
        val whiteKing = PieceFactory.createKing("king_w", Color.WHITE)
        val blackRook = PieceFactory.createRook("rook_b", Color.BLACK)

        val board =
            createBoard(
                mapOf(
                    Position(1, 1) to whiteKing,
                    Position(1, 8) to blackRook,
                ),
            )

        // Assert: The square 1,1 is under attack by the black rook.
        assertTrue(attackDetector.isSquareAttackedByEnemy(board, Position(1, 1), Color.WHITE))
    }

    @Test
    fun `isSquareAttackedByEnemy returns false if the path is blocked`() {
        val attackDetector = AttackDetector()

        // Setup: White king at 1,1, black rook at 1,8, but a white pawn at 1,2 blocking the path
        val whiteKing = PieceFactory.createKing("king_w", Color.WHITE)
        val whitePawn = PieceFactory.createPawn("pawn_w", Color.WHITE)
        val blackRook = PieceFactory.createRook("rook_b", Color.BLACK)

        val board =
            createBoard(
                mapOf(
                    Position(1, 1) to whiteKing,
                    Position(1, 2) to whitePawn,
                    Position(1, 8) to blackRook,
                ),
            )

        // Assert: The square 1,1 is not under attack because the pawn blocks the rook
        assertFalse(attackDetector.isSquareAttackedByEnemy(board, Position(1, 1), Color.WHITE))
    }

    @Test
    fun `isPieceAttacked returns true for the specific piece`() {
        val attackDetector = AttackDetector()

        // Setup: White king at 1,1, black rook at 1,8
        val whiteKing = PieceFactory.createKing("king_w", Color.WHITE)
        val blackRook = PieceFactory.createRook("rook_b", Color.BLACK)

        val board =
            createBoard(
                mapOf(
                    Position(1, 1) to whiteKing,
                    Position(1, 8) to blackRook,
                ),
            )

        // Assert: The king is under attack
        assertTrue(attackDetector.isPieceAttacked(board, PieceType.KING, Color.WHITE))
    }

    @Test
    fun `findPiecePosition finds the correct piece`() {
        val attackDetector = AttackDetector()
        val whiteKing = PieceFactory.createKing("king_w", Color.WHITE)
        val board =
            createBoard(
                mapOf(
                    Position(4, 4) to whiteKing,
                ),
            )

        val pos = attackDetector.findPiecePosition(board, PieceType.KING, Color.WHITE)
        assertEquals(Position(4, 4), pos)
    }
}
