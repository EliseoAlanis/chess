package edu.austral.dissis.chess.board

import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Bishop
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Piece
import edu.austral.dissis.chess.pieces.Rook
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class NormalBoardTest {
    private val whiteRook = Rook(Color.BLANCO)
    private val blackBishop = Bishop(Color.NEGRO)
    private val pos00 = Position(0, 0)
    private val pos11 = Position(1, 1)
    private val pos55 = Position(5, 5)

    private fun boardWith(vararg entries: Pair<Position, Piece>): NormalBoard = NormalBoard(mapOf(*entries))

    @Test
    fun `hasPieceAt returns true when piece exists`() {
        val board = boardWith(pos00 to whiteRook)
        assertTrue(board.hasPieceAt(pos00))
    }

    @Test
    fun `hasPieceAt returns false when position is empty`() {
        val board = boardWith(pos00 to whiteRook)
        assertFalse(board.hasPieceAt(pos55))
    }

    @Test
    fun `getPieceAt returns correct piece`() {
        val board = boardWith(pos00 to whiteRook)
        assertEquals(whiteRook, board.getPieceAt(pos00))
    }

    @Test
    fun `movePiece places piece at destination`() {
        val board = boardWith(pos00 to whiteRook)
        val result = board.movePiece(pos00, pos55)
        assertTrue(result.hasPieceAt(pos55))
        assertEquals(whiteRook, result.getPieceAt(pos55))
    }

    @Test
    fun `movePiece removes piece from origin`() {
        val board = boardWith(pos00 to whiteRook)
        val result = board.movePiece(pos00, pos55)
        assertFalse(result.hasPieceAt(pos00))
    }

    @Test
    fun `movePiece captures piece at destination`() {
        val board = boardWith(pos00 to whiteRook, pos55 to blackBishop)
        val result = board.movePiece(pos00, pos55)
        assertEquals(whiteRook, result.getPieceAt(pos55))
    }

    @Test
    fun `movePiece does not affect unrelated positions`() {
        val board = boardWith(pos00 to whiteRook, pos11 to blackBishop)
        val result = board.movePiece(pos00, pos55)
        assertEquals(blackBishop, result.getPieceAt(pos11))
    }

    @Test
    fun `board is immutable - original not modified after movePiece`() {
        val board = boardWith(pos00 to whiteRook)
        board.movePiece(pos00, pos55)
        assertTrue(board.hasPieceAt(pos00))
        assertFalse(board.hasPieceAt(pos55))
    }
}
