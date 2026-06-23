package edu.austral.dissis.chess.game

import edu.austral.dissis.common.pieces.Color
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class GameFactoriesTest {

    @Test
    fun `ClassicChessFactory creates a valid game`() {
        val factory = ClassicChessFactory()
        val (game, moveApplier) = factory.createGame()
        
        assertNotNull(game)
        assertNotNull(moveApplier)
        assertEquals(Color.WHITE, game.turnManager.currentTurn)
        assertEquals(8, game.board.size.rows)
        assertEquals(8, game.board.size.columns)
    }

    @Test
    fun `CapablancaChessFactory creates a valid game`() {
        val factory = CapablancaChessFactory()
        val (game, moveApplier) = factory.createGame()
        
        assertNotNull(game)
        assertNotNull(moveApplier)
        assertEquals(Color.WHITE, game.turnManager.currentTurn)
        assertEquals(8, game.board.size.rows)
        assertEquals(10, game.board.size.columns)
    }

    @Test
    fun `ExtinctChessFactory creates a valid game`() {
        val factory = ExtinctChessFactory()
        val (game, moveApplier) = factory.createGame()
        
        assertNotNull(game)
        assertNotNull(moveApplier)
        assertEquals(Color.WHITE, game.turnManager.currentTurn)
        assertEquals(8, game.board.size.rows)
        assertEquals(8, game.board.size.columns)
    }

    @Test
    fun `ProgressiveChessFactory creates a valid game`() {
        val factory = ProgressiveChessFactory()
        val (game, moveApplier) = factory.createGame()
        
        assertNotNull(game)
        assertNotNull(moveApplier)
        assertEquals(Color.WHITE, game.turnManager.currentTurn)
        assertEquals(8, game.board.size.rows)
        assertEquals(8, game.board.size.columns)
    }
}
