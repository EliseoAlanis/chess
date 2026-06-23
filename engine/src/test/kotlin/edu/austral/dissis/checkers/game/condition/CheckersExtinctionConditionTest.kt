package edu.austral.dissis.checkers.game.condition

import edu.austral.dissis.checkers.pieces.CheckersPieceFactory
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.game.GameStatus
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CheckersExtinctionConditionTest {

    private fun createBoard(positions: Map<Position, Piece>): Board = Board(positions, BoardSize(8, 8))

    @Test
    fun `checkStatus returns IN_PROGRESS if there are pieces alive`() {
        val condition = CheckersExtinctionCondition()
        val board = createBoard(mapOf(Position(1, 1) to CheckersPieceFactory.createMan("1", Color.WHITE)))
        
        val status = condition.checkStatus(board, Color.WHITE, emptyList())
        assertEquals(GameStatus.IN_PROGRESS, status)
    }

    @Test
    fun `checkStatus returns BLACK_WINS if white has no pieces`() {
        val condition = CheckersExtinctionCondition()
        val board = createBoard(emptyMap())
        
        val status = condition.checkStatus(board, Color.WHITE, emptyList())
        assertEquals(GameStatus.BLACK_WINS, status)
    }

    @Test
    fun `checkStatus returns WHITE_WINS if black has no pieces`() {
        val condition = CheckersExtinctionCondition()
        val board = createBoard(emptyMap())
        
        val status = condition.checkStatus(board, Color.BLACK, emptyList())
        assertEquals(GameStatus.WHITE_WINS, status)
    }
}
