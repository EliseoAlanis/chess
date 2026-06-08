package edu.austral.dissis.chess.exam
import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.board.BoardSize
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.chess.rules.BoardLimitsRule
import edu.austral.dissis.chess.rules.ClearPathRule
import edu.austral.dissis.chess.rules.CorrectTurnRule
import edu.austral.dissis.chess.rules.PieceMovementRule
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ChessRulesTest {
    private val boardSize = BoardSize(8, 8)

    @Test
    fun `BoardLimitsRule should prevent moving outside the board`() {
        val rule = BoardLimitsRule()
        val board = Board(size = boardSize)

        // Destino válido
        assertTrue(rule.isValid(board, Position(0, 0), Position(7, 7), Color.BLANCO))

        // Destinos inválidos (fuera de límites)
        assertFalse(rule.isValid(board, Position(0, 0), Position(8, 3), Color.BLANCO))
        assertFalse(rule.isValid(board, Position(0, 0), Position(3, -1), Color.BLANCO))
    }

    @Test
    fun `CorrectTurnRule should only allow moving pieces of current turn color`() {
        val rule = CorrectTurnRule()
        val positions =
            mapOf(
                Position(0, 0) to PieceFactory.createRook("r1", Color.BLANCO),
                Position(7, 0) to PieceFactory.createRook("r2", Color.NEGRO),
            )
        val board = Board(positions, boardSize)

        // Turno Blanco intenta mover pieza blanca -> OK
        assertTrue(rule.isValid(board, Position(0, 0), Position(1, 0), Color.BLANCO))

        // Turno Blanco intenta mover pieza negra -> Inválido
        assertFalse(rule.isValid(board, Position(7, 0), Position(6, 0), Color.BLANCO))
    }

    @Test
    fun `PieceMovementRule should validate geometric trajectories`() {
        val rule = PieceMovementRule()
        val positions =
            mapOf(
                Position(2, 2) to PieceFactory.createBishop("b1", Color.BLANCO),
            )
        val board = Board(positions, boardSize)

        // Alfil moviéndose en diagonal -> OK
        assertTrue(rule.isValid(board, Position(2, 2), Position(5, 5), Color.BLANCO))

        // Alfil moviéndose recto -> Inválido
        assertFalse(rule.isValid(board, Position(2, 2), Position(2, 5), Color.BLANCO))
    }

    @Test
    fun `ClearPathRule should block non-jumping pieces if path is obstructed`() {
        val rule = ClearPathRule()

        // Colocamos una torre blanca en (0,0) y un obstáculo en (0,2)
        val positions =
            mapOf(
                Position(0, 0) to PieceFactory.createRook("r1", Color.BLANCO),
                Position(0, 2) to PieceFactory.createBishop("obstaculo", Color.NEGRO),
            )
        val board = Board(positions, boardSize)

        // Trayectoria libre hasta (0,1) -> OK
        assertTrue(rule.isValid(board, Position(0, 0), Position(0, 1), Color.BLANCO))

        // Trayectoria obstruida pasando el obstáculo hasta (0,3) -> Inválido
        assertFalse(rule.isValid(board, Position(0, 0), Position(0, 3), Color.BLANCO))
    }

    @Test
    fun `ClearPathRule should allow jumping pieces to ignore obstacles`() {
        val rule = ClearPathRule()

        // Caballo en (0,1) y obstáculo directo en la fila de al lado
        val positions =
            mapOf(
                Position(0, 1) to PieceFactory.createKnight("n1", Color.BLANCO),
                Position(1, 1) to PieceFactory.createRook("obstaculo", Color.BLANCO),
            )
        val board = Board(positions, boardSize)

        // El caballo salta el obstáculo sin problemas hacia (2,2)
        assertTrue(rule.isValid(board, Position(0, 1), Position(2, 2), Color.BLANCO))
    }
}
