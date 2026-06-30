package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.movement.validator.AttackDetector
import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NoSelfCheckRuleTest {

    private fun createBoard(positions: Map<Position, Piece>): Board = Board(positions, BoardSize(8, 8))

    @Test
    fun `NoSelfCheckRule returns false if move leaves king under attack`() {
        val attackDetector = AttackDetector()
        val rule = NoSelfCheckRule(attackDetector)
        
        // Setup: White king at 1,1. Black rook at 1,8. White pawn at 1,2 blocking the check.
        // Moving the pawn out of the way (e.g. to 2,2) should be invalid because it leaves king in check.
        val whiteKing = PieceFactory.createKing("king_w", Color.WHITE)
        val whitePawn = PieceFactory.createPawn("pawn_w", Color.WHITE)
        val blackRook = PieceFactory.createRook("rook_b", Color.BLACK)
        
        val board = createBoard(mapOf(
            Position(1, 1) to whiteKing,
            Position(1, 2) to whitePawn,
            Position(1, 8) to blackRook
        ))
        
        val vector = MovementVector(Position(1, 2), Position(2, 2)) // pawn moves away
        
        assertFalse(rule.evaluate(MoveContext(board, vector, Color.WHITE)) is RuleResult.Valid)
    }

    @Test
    fun `NoSelfCheckRule returns true if move is safe`() {
        val attackDetector = AttackDetector()
        val rule = NoSelfCheckRule(attackDetector)
        
        val whiteKing = PieceFactory.createKing("king_w", Color.WHITE)
        val whitePawn = PieceFactory.createPawn("pawn_w", Color.WHITE)
        
        val board = createBoard(mapOf(
            Position(1, 1) to whiteKing,
            Position(2, 2) to whitePawn
        ))
        
        val vector = MovementVector(Position(2, 2), Position(2, 3)) // pawn moves forward
        
        assertTrue(rule.evaluate(MoveContext(board, vector, Color.WHITE)) is RuleResult.Valid)
    }
}
