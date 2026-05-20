package edu.austral.dissis.chess.rules

import edu.austral.dissis.chess.board.NormalBoard
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Rook
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ClearPathRuleTest {
    private val rule = ClearPathRule()
    private val turn = Color.BLANCO

    @Test
    fun `horizontal path with no pieces is valid`() {
        val board = NormalBoard(mapOf(Position(0, 0) to Rook(turn)))
        assertTrue(rule.isValid(board, Position(0, 0), Position(0, 5), turn))
    }

    @Test
    fun `horizontal path blocked by piece is not valid`() {
        val board =
            NormalBoard(
                mapOf(
                    Position(0, 0) to Rook(turn),
                    Position(0, 3) to Rook(Color.NEGRO),
                ),
            )
        assertFalse(rule.isValid(board, Position(0, 0), Position(0, 5), turn))
    }

    @Test
    fun `vertical path with no pieces is valid`() {
        val board = NormalBoard(mapOf(Position(0, 0) to Rook(turn)))
        assertTrue(rule.isValid(board, Position(0, 0), Position(5, 0), turn))
    }

    @Test
    fun `vertical path blocked by piece is not valid`() {
        val board =
            NormalBoard(
                mapOf(
                    Position(0, 0) to Rook(turn),
                    Position(3, 0) to Rook(Color.NEGRO),
                ),
            )
        assertFalse(rule.isValid(board, Position(0, 0), Position(5, 0), turn))
    }

    @Test
    fun `diagonal path with no pieces is valid`() {
        val board = NormalBoard(mapOf(Position(0, 0) to Rook(turn)))
        assertTrue(rule.isValid(board, Position(0, 0), Position(4, 4), turn))
    }

    @Test
    fun `diagonal path blocked by piece is not valid`() {
        val board =
            NormalBoard(
                mapOf(
                    Position(0, 0) to Rook(turn),
                    Position(2, 2) to Rook(Color.NEGRO),
                ),
            )
        assertFalse(rule.isValid(board, Position(0, 0), Position(4, 4), turn))
    }

    @Test
    fun `adjacent move with clear path is valid`() {
        val board = NormalBoard(mapOf(Position(3, 3) to Rook(turn)))
        assertTrue(rule.isValid(board, Position(3, 3), Position(3, 4), turn))
    }

    @Test
    fun `destination occupied does not block path check`() {
        val board =
            NormalBoard(
                mapOf(
                    Position(0, 0) to Rook(turn),
                    Position(0, 5) to Rook(Color.NEGRO),
                ),
            )
        // destination is enemy - path UP TO destination should be clear
        assertTrue(rule.isValid(board, Position(0, 0), Position(0, 5), turn))
    }
}
