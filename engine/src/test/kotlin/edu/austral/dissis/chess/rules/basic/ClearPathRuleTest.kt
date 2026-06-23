package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ClearPathRuleTest {

    private fun createBoard(positions: Map<Position, Piece>): Board = Board(positions, BoardSize(8, 8))

    @Test
    fun `ClearPathRule returns true if path has no pieces in between`() {
        val rule = ClearPathRule()
        
        val originPos = Position(1, 1)
        val destPos = Position(1, 5)
        
        // Rook can move horizontally, creating intermediate positions (1,2), (1,3), (1,4)
        val rook = PieceFactory.createRook("rook_w", Color.WHITE)
        val board = createBoard(mapOf(originPos to rook))
        val path = Path(originPos, destPos)
        
        assertTrue(rule.isValid(board, path, Color.WHITE))
    }

    @Test
    fun `ClearPathRule returns false if there is a piece in the path`() {
        val rule = ClearPathRule()
        
        val originPos = Position(1, 1)
        val destPos = Position(1, 5)
        val blockPos = Position(1, 3)
        
        val rook = PieceFactory.createRook("rook_w", Color.WHITE)
        val pawn = PieceFactory.createPawn("pawn_b", Color.BLACK)
        
        val board = createBoard(mapOf(
            originPos to rook,
            blockPos to pawn
        ))
        
        val path = Path(originPos, destPos)
        
        assertFalse(rule.isValid(board, path, Color.WHITE))
    }
}
