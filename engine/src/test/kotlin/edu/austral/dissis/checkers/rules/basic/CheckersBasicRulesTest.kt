package edu.austral.dissis.checkers.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import edu.austral.dissis.checkers.pieces.CheckersPieceFactory
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CheckersBasicRulesTest {
    private fun createBoard(positions: Map<Position, Piece>): Board = Board(positions, BoardSize(8, 8))

    @Test
    fun `CorrectTurnRule returns true if piece matches current turn color`() {
        val rule = CorrectTurnRule()
        val whitePiece = CheckersPieceFactory.createMan("1", Color.WHITE)
        val board = createBoard(mapOf(Position(1, 1) to whitePiece))
        
        assertTrue(rule.isValid(board, Path(Position(1, 1), Position(2, 2)), Color.WHITE))
    }

    @Test
    fun `CorrectTurnRule returns false if piece does not match current turn color`() {
        val rule = CorrectTurnRule()
        val blackPiece = CheckersPieceFactory.createMan("1", Color.BLACK)
        val board = createBoard(mapOf(Position(1, 1) to blackPiece))
        
        assertFalse(rule.isValid(board, Path(Position(1, 1), Position(2, 2)), Color.WHITE))
    }

    @Test
    fun `BoardLimitsRule returns false if destination is out of bounds`() {
        val rule = BoardLimitsRule()
        val board = createBoard(emptyMap())
        
        assertFalse(rule.isValid(board, Path(Position(1, 1), Position(9, 1)), Color.WHITE))
        assertFalse(rule.isValid(board, Path(Position(1, 1), Position(1, 9)), Color.WHITE))
        assertFalse(rule.isValid(board, Path(Position(1, 1), Position(0, 1)), Color.WHITE))
    }

    @Test
    fun `BoardLimitsRule returns true if destination is within bounds`() {
        val rule = BoardLimitsRule()
        val board = createBoard(emptyMap())
        
        assertTrue(rule.isValid(board, Path(Position(1, 1), Position(8, 8)), Color.WHITE))
    }

    @Test
    fun `EmptyDestinationRule returns false if destination is occupied`() {
        val rule = EmptyDestinationRule()
        val whitePiece = CheckersPieceFactory.createMan("1", Color.WHITE)
        val board = createBoard(mapOf(Position(2, 2) to whitePiece))
        
        assertFalse(rule.isValid(board, Path(Position(1, 1), Position(2, 2)), Color.WHITE))
    }

    @Test
    fun `EmptyDestinationRule returns true if destination is empty`() {
        val rule = EmptyDestinationRule()
        val board = createBoard(emptyMap())
        
        assertTrue(rule.isValid(board, Path(Position(1, 1), Position(2, 2)), Color.WHITE))
    }
}
