package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.movement.atomics.StraightMovement
import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.movement.ClearPathMovement
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ClearPathRuleTest {
    private fun createBoard(positions: Map<Position, Piece>): Board = Board(positions, BoardSize(8, 8))

    @Test
    fun `ClearPathMovement returns Valid if path has no pieces in between`() {
        val rule = ClearPathMovement(StraightMovement())

        val originPos = Position(1, 1)
        val destPos = Position(1, 5)

        val rook = PieceFactory.createRook("rook_w", Color.WHITE)
        val board = createBoard(mapOf(originPos to rook))
        val vector = MovementVector(originPos, destPos)

        val context = MoveContext(board, vector, Color.WHITE)
        assertTrue(rule.evaluate(context) is RuleResult.Valid)
    }

    @Test
    fun `ClearPathMovement returns Invalid if there is a piece in the path`() {
        val rule = ClearPathMovement(StraightMovement())

        val originPos = Position(1, 1)
        val destPos = Position(1, 5)
        val blockPos = Position(1, 3)

        val rook = PieceFactory.createRook("rook_w", Color.WHITE)
        val pawn = PieceFactory.createPawn("pawn_b", Color.BLACK)

        val board =
            createBoard(
                mapOf(
                    originPos to rook,
                    blockPos to pawn,
                ),
            )

        val vector = MovementVector(originPos, destPos)

        val context = MoveContext(board, vector, Color.WHITE)
        assertTrue(rule.evaluate(context) is RuleResult.Invalid)
    }
}
