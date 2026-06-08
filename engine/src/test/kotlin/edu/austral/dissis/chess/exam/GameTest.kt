package edu.austral.dissis.chess.exam
import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.board.BoardSize
import edu.austral.dissis.chess.board.ClassicBoardInitializer
import edu.austral.dissis.chess.game.Game
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.rules.BoardLimitsRule
import edu.austral.dissis.chess.rules.ClearPathRule
import edu.austral.dissis.chess.rules.CorrectTurnRule
import edu.austral.dissis.chess.rules.PieceMovementRule
import edu.austral.dissis.chess.turn.StandardTurnManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GameTest {

    @Test
    fun `should play a valid move successfully`() {
        val size = BoardSize(8, 8)
        val board = Board(ClassicBoardInitializer().generateInitialPositions(), size)
        val rules = listOf(BoardLimitsRule(), CorrectTurnRule(), PieceMovementRule(), ClearPathRule())
        val game = Game(board, rules, StandardTurnManager())

        // El caballo blanco en (0,1) salta a (2,2)
        val origin = Position(0, 1)
        val destination = Position(2, 2)

        val nextGame = game.playMove(origin, destination)

        // 1. El tablero debió cambiar
        assertNotEquals(game.board, nextGame.board)
        assertTrue(nextGame.board.hasPieceAt(destination))

        // 2. El turno debió avanzar a NEGRO
        assertEquals(Color.NEGRO, nextGame.turnManager.currentTurn)

        // 3. El historial debió registrar el movimiento
        assertEquals(1, nextGame.moveHistory.size)
        assertEquals(origin, nextGame.moveHistory.first().origin)
        assertEquals(destination, nextGame.moveHistory.first().destination)
    }

    @Test
    fun `should reject illegal move and preserve game state`() {
        val size = BoardSize(8, 8)
        val board = Board(ClassicBoardInitializer().generateInitialPositions(), size)
        val rules = listOf(BoardLimitsRule(), CorrectTurnRule(), PieceMovementRule(), ClearPathRule())
        val game = Game(board, rules, StandardTurnManager())

        // Intentar mover la Torre Blanca (0,0) saltando al caballo (0,1) -> Inválido por ClearPathRule
        val origin = Position(0, 0)
        val destination = Position(0, 2)

        val contextAfterMove = game.playMove(origin, destination)

        // Todo se tiene que quedar igual (Inmutabilidad)
        assertSame(game, contextAfterMove)
        assertEquals(Color.BLANCO, contextAfterMove.turnManager.currentTurn)
        assertTrue(contextAfterMove.moveHistory.isEmpty())
    }
}
