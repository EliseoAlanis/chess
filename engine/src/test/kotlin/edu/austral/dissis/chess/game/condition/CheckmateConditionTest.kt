package edu.austral.dissis.chess.game.condition

import edu.austral.dissis.chess.movement.validator.AttackDetector
import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.game.GameStatus
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CheckmateConditionTest {

    private fun createBoard(positions: Map<Position, Piece>): Board = Board(positions, BoardSize(8, 8))

    @Test
    fun `checkStatus returns IN_PROGRESS if there are valid moves`() {
        val attackDetector = AttackDetector()
        // Mocking MoveValidator to always return true (moves are valid)
        val alwaysValidValidator = object : MoveValidator {
            override fun validateMove(board: Board, turn: Color, history: List<MoveRecord>, path: Path) = edu.austral.dissis.common.rules.RuleResult.Valid()
            override fun canMove(board: Board, color: Color, path: Path, moveHistory: List<MoveRecord>) = true
        }
        
        val condition = CheckmateCondition(alwaysValidValidator, attackDetector)
        val board = createBoard(mapOf(Position(1, 1) to PieceFactory.createKing("king_w", Color.WHITE)))
        
        val status = condition.checkStatus(board, Color.WHITE, emptyList())
        assertEquals(GameStatus.IN_PROGRESS, status)
    }

    @Test
    fun `checkStatus returns BLACK_WINS if white king is attacked and has no valid moves`() {
        val attackDetector = AttackDetector()
        // Mocking MoveValidator to always return false (no valid moves)
        val noValidValidator = object : MoveValidator {
            override fun validateMove(board: Board, turn: Color, history: List<MoveRecord>, path: Path) = edu.austral.dissis.common.rules.RuleResult.Invalid
            override fun canMove(board: Board, color: Color, path: Path, moveHistory: List<MoveRecord>) = false
        }
        
        val condition = CheckmateCondition(noValidValidator, attackDetector)
        
        // Setup: White king at 1,1. Black rook at 1,8 attacks it.
        val board = createBoard(mapOf(
            Position(1, 1) to PieceFactory.createKing("king_w", Color.WHITE),
            Position(1, 8) to PieceFactory.createRook("rook_b", Color.BLACK)
        ))
        
        val status = condition.checkStatus(board, Color.WHITE, emptyList())
        assertEquals(GameStatus.BLACK_WINS, status)
    }

    @Test
    fun `checkStatus returns DRAW if king is not attacked but has no valid moves`() {
        val attackDetector = AttackDetector()
        // Mocking MoveValidator to always return false (no valid moves)
        val noValidValidator = object : MoveValidator {
            override fun validateMove(board: Board, turn: Color, history: List<MoveRecord>, path: Path) = edu.austral.dissis.common.rules.RuleResult.Invalid
            override fun canMove(board: Board, color: Color, path: Path, moveHistory: List<MoveRecord>) = false
        }
        
        val condition = CheckmateCondition(noValidValidator, attackDetector)
        
        // Setup: White king at 1,1. Not attacked by anything.
        val board = createBoard(mapOf(
            Position(1, 1) to PieceFactory.createKing("king_w", Color.WHITE)
        ))
        
        val status = condition.checkStatus(board, Color.WHITE, emptyList())
        assertEquals(GameStatus.DRAW, status)
    }
}
