package edu.austral.dissis.chess.rules

import edu.austral.dissis.chess.board.NormalBoard
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Rook
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CorrectTurnRuleTest {
    private val rule = CorrectTurnRule()

    private fun boardWith(
        pos: Position,
        piece: edu.austral.dissis.chess.pieces.Piece,
    ) = NormalBoard(mapOf(pos to piece))

    @Test
    fun `white piece on white turn is valid`() {
        val board = boardWith(Position(0, 0), Rook(Color.BLANCO))
        assertTrue(rule.isValid(board, Position(0, 0), Position(0, 5), Color.BLANCO))
    }

    @Test
    fun `black piece on black turn is valid`() {
        val board = boardWith(Position(7, 0), Rook(Color.NEGRO))
        assertTrue(rule.isValid(board, Position(7, 0), Position(7, 5), Color.NEGRO))
    }

    @Test
    fun `white piece on black turn is not valid`() {
        val board = boardWith(Position(0, 0), Rook(Color.BLANCO))
        assertFalse(rule.isValid(board, Position(0, 0), Position(0, 5), Color.NEGRO))
    }

    @Test
    fun `black piece on white turn is not valid`() {
        val board = boardWith(Position(7, 0), Rook(Color.NEGRO))
        assertFalse(rule.isValid(board, Position(7, 0), Position(7, 5), Color.BLANCO))
    }
}
