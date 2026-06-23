package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.movement.atomics.ForwardMovement
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BasicRulesTest {
    private fun createDummyPiece(color: Color): Piece =
        Piece(
            id = "test-piece",
            color = color,
            pieceType = PieceType.PAWN,
            movements = ForwardMovement(1),
            captureMovements = ForwardMovement(1),
        )

    private fun createBoard(positions: Map<Position, Piece>): Board = Board(positions, BoardSize(8, 8))

    @Test
    fun `BoardLimitsRule returns false if destination is out of bounds`() {
        val rule = BoardLimitsRule()
        val board = createBoard(emptyMap())

        val pathOutsideCols = Path(Position(1, 1), Position(1, 9))
        val pathOutsideRows = Path(Position(1, 1), Position(9, 1))
        val pathNegative = Path(Position(1, 1), Position(0, 1))

        assertFalse(rule.isValid(board, pathOutsideCols, Color.WHITE))
        assertFalse(rule.isValid(board, pathOutsideRows, Color.WHITE))
        assertFalse(rule.isValid(board, pathNegative, Color.WHITE))
    }

    @Test
    fun `BoardLimitsRule returns true if destination is within bounds`() {
        val rule = BoardLimitsRule()
        val board = createBoard(emptyMap())

        val validPath = Path(Position(1, 1), Position(8, 8))

        assertTrue(rule.isValid(board, validPath, Color.WHITE))
    }

    @Test
    fun `FriendlyFireRule returns false if destination has piece of same color`() {
        val rule = FriendlyFireRule()

        val originPos = Position(1, 1)
        val destPos = Position(2, 2)

        val whitePiece1 = createDummyPiece(Color.WHITE)
        val whitePiece2 = createDummyPiece(Color.WHITE)

        val board =
            createBoard(
                mapOf(
                    originPos to whitePiece1,
                    destPos to whitePiece2,
                ),
            )

        val path = Path(originPos, destPos)

        assertFalse(rule.isValid(board, path, Color.WHITE))
    }

    @Test
    fun `FriendlyFireRule returns true if destination is empty`() {
        val rule = FriendlyFireRule()

        val originPos = Position(1, 1)
        val destPos = Position(2, 2)

        val whitePiece = createDummyPiece(Color.WHITE)

        val board =
            createBoard(
                mapOf(
                    originPos to whitePiece,
                ),
            )

        val path = Path(originPos, destPos)

        assertTrue(rule.isValid(board, path, Color.WHITE))
    }

    @Test
    fun `CorrectTurnRule returns true if piece matches current turn color`() {
        val rule = CorrectTurnRule()

        val originPos = Position(1, 1)
        val whitePiece = createDummyPiece(Color.WHITE)

        val board =
            createBoard(
                mapOf(
                    originPos to whitePiece,
                ),
            )

        val path = Path(originPos, Position(2, 1))

        assertTrue(rule.isValid(board, path, Color.WHITE))
    }

    @Test
    fun `CorrectTurnRule returns false if piece does not match current turn color`() {
        val rule = CorrectTurnRule()

        val originPos = Position(1, 1)
        val blackPiece = createDummyPiece(Color.BLACK)

        val board =
            createBoard(
                mapOf(
                    originPos to blackPiece,
                ),
            )

        val path = Path(originPos, Position(2, 1))

        assertFalse(rule.isValid(board, path, Color.WHITE))
    }
}
