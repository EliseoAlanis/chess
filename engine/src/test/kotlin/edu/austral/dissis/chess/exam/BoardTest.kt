package edu.austral.dissis.chess.exam
import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.board.BoardSize
import edu.austral.dissis.chess.board.ClassicBoardInitializer
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BoardTest {
    @Test
    fun `should initialize board with classic setup`() {
        val size = BoardSize(8, 8)
        val initializer = ClassicBoardInitializer()
        val board = Board(initializer.generateInitialPositions(), size)

        // Verificar tamaño
        assertEquals(8, board.size.rows)
        assertEquals(8, board.size.columns)

        // Verificar que existan piezas clave en sus posiciones iniciales
        assertTrue(board.hasPieceAt(Position(0, 0))) // Torre Blanca
        assertTrue(board.hasPieceAt(Position(7, 3))) // Reina Negra

        val whiteRook = board.getPieceAt(Position(0, 0))
        assertEquals(Color.BLANCO, whiteRook.color)
    }

    @Test
    fun `should move piece correctly`() {
        val size = BoardSize(8, 8)
        val initializer = ClassicBoardInitializer()
        var board = Board(initializer.generateInitialPositions(), size)

        val origin = Position(0, 1) // Caballo Blanco
        val destination = Position(2, 2)

        assertTrue(board.hasPieceAt(origin))
        assertFalse(board.hasPieceAt(destination))

        // Ejecutar movimiento
        board = board.movePiece(origin, destination)

        // Verificar nuevo estado
        assertFalse(board.hasPieceAt(origin))
        assertTrue(board.hasPieceAt(destination))
    }

    @Test
    fun `should return same board when trying to move from an empty position`() {
        val board = Board(size = BoardSize(8, 8))
        val emptyPosition = Position(4, 4)
        val destination = Position(5, 4)

        val updatedBoard = board.movePiece(emptyPosition, destination)

        assertSame(board, updatedBoard)
    }
}
