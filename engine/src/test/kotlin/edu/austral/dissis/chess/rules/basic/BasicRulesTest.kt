package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.movement.atomics.ForwardMovement
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BasicRulesTest {
    private fun createDummyPiece(color: Color): Piece =
        Piece(
            id = "test-piece",
            color = color,
            pieceType = PieceType.PAWN,
            rules = listOf(ForwardMovement(1)),
            captureRules = listOf(ForwardMovement(1)),
        )

    private fun createBoard(positions: Map<Position, Piece>): Board = Board(positions, BoardSize(8, 8))

    @Test
    fun `BoardLimitsRule returns false if destination is out of bounds`() {
        val rule = BoardLimitsRule()
        val board = createBoard(emptyMap())

        val vectorOutsideCols = MovementVector(Position(1, 1), Position(1, 9))
        val vectorOutsideRows = MovementVector(Position(1, 1), Position(9, 1))
        val vectorNegative = MovementVector(Position(1, 1), Position(0, 1))

        assertFalse(rule.evaluate(MoveContext(board, vectorOutsideCols, Color.WHITE)) is RuleResult.Valid)
        assertFalse(rule.evaluate(MoveContext(board, vectorOutsideRows, Color.WHITE)) is RuleResult.Valid)
        assertFalse(rule.evaluate(MoveContext(board, vectorNegative, Color.WHITE)) is RuleResult.Valid)
    }

    @Test
    fun `BoardLimitsRule returns true if destination is within bounds`() {
        val rule = BoardLimitsRule()
        val board = createBoard(emptyMap())

        val validVector = MovementVector(Position(1, 1), Position(8, 8))

        assertTrue(rule.evaluate(MoveContext(board, validVector, Color.WHITE)) is RuleResult.Valid)
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

        val vector = MovementVector(originPos, destPos)

        assertFalse(rule.evaluate(MoveContext(board, vector, Color.WHITE)) is RuleResult.Valid)
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

        val vector = MovementVector(originPos, destPos)

        assertTrue(rule.evaluate(MoveContext(board, vector, Color.WHITE)) is RuleResult.Valid)
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

        val vector = MovementVector(originPos, Position(2, 1))

        assertTrue(rule.evaluate(MoveContext(board, vector, Color.WHITE)) is RuleResult.Valid)
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

        val vector = MovementVector(originPos, Position(2, 1))

        assertFalse(rule.evaluate(MoveContext(board, vector, Color.WHITE)) is RuleResult.Valid)
    }
}
