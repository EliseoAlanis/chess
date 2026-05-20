package edu.austral.dissis.chess.game

import edu.austral.dissis.chess.board.NormalBoard
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Queen
import edu.austral.dissis.chess.pieces.Rook
import edu.austral.dissis.chess.rules.BoardLimitsRule
import edu.austral.dissis.chess.rules.ChessRule
import edu.austral.dissis.chess.rules.ClearPathRule
import edu.austral.dissis.chess.rules.CorrectTurnRule
import edu.austral.dissis.chess.rules.PieceMovementRule
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GameTest {
    private val standardRules: List<ChessRule> =
        listOf(
            BoardLimitsRule(7, 7),
            CorrectTurnRule(),
            PieceMovementRule(),
            ClearPathRule(),
        )

    private fun gameWith(vararg entries: Pair<Position, edu.austral.dissis.chess.pieces.Piece>): Game =
        Game(NormalBoard(mapOf(*entries)), Color.BLANCO, standardRules)

    @Test
    fun `valid move updates board`() {
        val origin = Position(0, 0)
        val dest = Position(0, 5)
        val game = gameWith(origin to Rook(Color.BLANCO))
        val result = game.playMove(origin, dest)
        assertTrue(result.board.hasPieceAt(dest))
        assertFalse(result.board.hasPieceAt(origin))
    }

    @Test
    fun `valid move alternates turn from white to black`() {
        val origin = Position(0, 0)
        val dest = Position(0, 5)
        val game = gameWith(origin to Rook(Color.BLANCO))
        val result = game.playMove(origin, dest)
        assertEquals(Color.NEGRO, result.currentTurn)
    }

    @Test
    fun `wrong turn move does not change game state`() {
        val origin = Position(0, 0)
        val dest = Position(0, 5)
        // Board has white turn but we place a black piece
        val board = NormalBoard(mapOf(origin to Rook(Color.NEGRO)))
        val game = Game(board, Color.BLANCO, standardRules)
        val result = game.playMove(origin, dest)
        assertEquals(Color.BLANCO, result.currentTurn)
        assertFalse(result.board.hasPieceAt(dest))
    }

    @Test
    fun `invalid movement pattern does not move piece`() {
        val origin = Position(0, 0)
        val dest = Position(3, 5) // rook can't go here
        val game = gameWith(origin to Rook(Color.BLANCO))
        val result = game.playMove(origin, dest)
        assertTrue(result.board.hasPieceAt(origin))
        assertFalse(result.board.hasPieceAt(dest))
    }

    @Test
    fun `move to out-of-bounds destination is rejected`() {
        val origin = Position(0, 0)
        val dest = Position(0, 9)
        val game = gameWith(origin to Rook(Color.BLANCO))
        val result = game.playMove(origin, dest)
        assertTrue(result.board.hasPieceAt(origin))
    }

    @Test
    fun `move with no piece at origin returns same game`() {
        val origin = Position(0, 0)
        val emptyOrigin = Position(4, 4)
        val game = gameWith(origin to Rook(Color.BLANCO))
        val result = game.playMove(emptyOrigin, Position(4, 5))
        assertEquals(Color.BLANCO, result.currentTurn)
    }

    @Test
    fun `valid capture removes enemy piece`() {
        val whitePos = Position(0, 0)
        val blackPos = Position(0, 5)
        val game = gameWith(whitePos to Rook(Color.BLANCO), blackPos to Rook(Color.NEGRO))
        val result = game.playMove(whitePos, blackPos)
        assertEquals(Rook(Color.BLANCO), result.board.getPieceAt(blackPos))
    }

    @Test
    fun `two consecutive valid moves alternate turns correctly`() {
        val whitePos = Position(0, 0)
        val blackPos = Position(7, 7)
        val game = gameWith(whitePos to Rook(Color.BLANCO), blackPos to Rook(Color.NEGRO))
        val after1 = game.playMove(whitePos, Position(0, 4))
        assertEquals(Color.NEGRO, after1.currentTurn)
        val after2 = after1.playMove(blackPos, Position(7, 4))
        assertEquals(Color.BLANCO, after2.currentTurn)
    }

    @Test
    fun `blocked path move is rejected`() {
        val origin = Position(0, 0)
        val blocker = Position(0, 3)
        val dest = Position(0, 6)
        val game = gameWith(origin to Rook(Color.BLANCO), blocker to Queen(Color.NEGRO))
        val result = game.playMove(origin, dest)
        assertTrue(result.board.hasPieceAt(origin))
        assertFalse(result.board.hasPieceAt(dest))
    }
}
