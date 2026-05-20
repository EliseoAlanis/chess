package edu.austral.dissis.chess.rules

import edu.austral.dissis.chess.board.NormalBoard
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Bishop
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Queen
import edu.austral.dissis.chess.pieces.Rook
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class PieceMovementRuleTest {
    private val rule = PieceMovementRule()
    private val turn = Color.BLANCO

    private fun boardWith(
        pos: Position,
        piece: edu.austral.dissis.chess.pieces.Piece,
    ) = NormalBoard(mapOf(pos to piece))

    @Test
    fun `rook moving straight horizontally is valid`() {
        val board = boardWith(Position(3, 0), Rook(turn))
        assertTrue(rule.isValid(board, Position(3, 0), Position(3, 7), turn))
    }

    @Test
    fun `rook moving diagonally is not valid`() {
        val board = boardWith(Position(3, 3), Rook(turn))
        assertFalse(rule.isValid(board, Position(3, 3), Position(5, 5), turn))
    }

    @Test
    fun `bishop moving diagonally is valid`() {
        val board = boardWith(Position(0, 0), Bishop(turn))
        assertTrue(rule.isValid(board, Position(0, 0), Position(4, 4), turn))
    }

    @Test
    fun `bishop moving straight is not valid`() {
        val board = boardWith(Position(0, 0), Bishop(turn))
        assertFalse(rule.isValid(board, Position(0, 0), Position(0, 4), turn))
    }

    @Test
    fun `queen moving straight is valid`() {
        val board = boardWith(Position(4, 4), Queen(turn))
        assertTrue(rule.isValid(board, Position(4, 4), Position(4, 7), turn))
    }

    @Test
    fun `queen moving diagonally is valid`() {
        val board = boardWith(Position(4, 4), Queen(turn))
        assertTrue(rule.isValid(board, Position(4, 4), Position(7, 7), turn))
    }

    @Test
    fun `queen moving off-axis is not valid`() {
        val board = boardWith(Position(0, 0), Queen(turn))
        assertFalse(rule.isValid(board, Position(0, 0), Position(2, 5), turn))
    }
}
