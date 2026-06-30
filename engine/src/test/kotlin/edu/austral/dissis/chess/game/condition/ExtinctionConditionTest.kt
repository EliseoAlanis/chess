package edu.austral.dissis.chess.game.condition

import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.game.GameStatus
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ExtinctionConditionTest {
    private fun createBoard(positions: Map<Position, Piece>): Board = Board(positions, BoardSize(8, 8))

    @Test
    fun `checkStatus returns IN_PROGRESS if all expected piece types are present`() {
        val condition = ExtinctionCondition()

        val board =
            createBoard(
                mapOf(
                    Position(1, 1) to PieceFactory.createPawn("p1", Color.WHITE),
                    Position(1, 2) to PieceFactory.createRook("r1", Color.WHITE),
                    Position(1, 3) to PieceFactory.createKnight("n1", Color.WHITE),
                    Position(1, 4) to PieceFactory.createBishop("b1", Color.WHITE),
                    Position(1, 5) to PieceFactory.createQueen("q1", Color.WHITE),
                    Position(1, 6) to PieceFactory.createKing("k1", Color.WHITE),
                ),
            )

        val status = condition.checkStatus(board, Color.WHITE, emptyList())
        assertEquals(GameStatus.IN_PROGRESS, status)
    }

    @Test
    fun `checkStatus returns BLACK_WINS if white is missing a piece type`() {
        val condition = ExtinctionCondition()

        // Missing the Queen for White
        val board =
            createBoard(
                mapOf(
                    Position(1, 1) to PieceFactory.createPawn("p1", Color.WHITE),
                    Position(1, 2) to PieceFactory.createRook("r1", Color.WHITE),
                    Position(1, 3) to PieceFactory.createKnight("n1", Color.WHITE),
                    Position(1, 4) to PieceFactory.createBishop("b1", Color.WHITE),
                    Position(1, 6) to PieceFactory.createKing("k1", Color.WHITE),
                ),
            )

        val status = condition.checkStatus(board, Color.WHITE, emptyList())
        assertEquals(GameStatus.BLACK_WINS, status)
    }

    @Test
    fun `checkStatus returns WHITE_WINS if black is missing a piece type`() {
        val condition = ExtinctionCondition()

        // Missing the King for Black
        val board =
            createBoard(
                mapOf(
                    Position(1, 1) to PieceFactory.createPawn("p1", Color.BLACK),
                    Position(1, 2) to PieceFactory.createRook("r1", Color.BLACK),
                    Position(1, 3) to PieceFactory.createKnight("n1", Color.BLACK),
                    Position(1, 4) to PieceFactory.createBishop("b1", Color.BLACK),
                    Position(1, 5) to PieceFactory.createQueen("q1", Color.BLACK),
                ),
            )

        val status = condition.checkStatus(board, Color.BLACK, emptyList())
        assertEquals(GameStatus.WHITE_WINS, status)
    }
}
