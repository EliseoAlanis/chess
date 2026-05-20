package edu.austral.dissis.chess.rules

import edu.austral.dissis.chess.board.NormalBoard
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Rook
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BoardLimitsRuleTest {
    private val rule = BoardLimitsRule(7, 7)
    private val board = NormalBoard(mapOf(Position(0, 0) to Rook(Color.BLANCO)))
    private val turn = Color.BLANCO

    @Test
    fun `destination within bounds is valid`() {
        assertTrue(rule.isValid(board, Position(0, 0), Position(4, 4), turn))
    }

    @Test
    fun `destination at max boundary is valid`() {
        assertTrue(rule.isValid(board, Position(0, 0), Position(7, 7), turn))
    }

    @Test
    fun `destination at origin zero is valid`() {
        assertTrue(rule.isValid(board, Position(3, 3), Position(0, 0), turn))
    }

    @Test
    fun `destination row below zero is not valid`() {
        assertFalse(rule.isValid(board, Position(0, 0), Position(-1, 3), turn))
    }

    @Test
    fun `destination column below zero is not valid`() {
        assertFalse(rule.isValid(board, Position(0, 0), Position(3, -1), turn))
    }

    @Test
    fun `destination row above max is not valid`() {
        assertFalse(rule.isValid(board, Position(0, 0), Position(8, 3), turn))
    }

    @Test
    fun `destination column above max is not valid`() {
        assertFalse(rule.isValid(board, Position(0, 0), Position(3, 8), turn))
    }

    @Test
    fun `both row and column out of bounds is not valid`() {
        assertFalse(rule.isValid(board, Position(0, 0), Position(9, 9), turn))
    }
}
