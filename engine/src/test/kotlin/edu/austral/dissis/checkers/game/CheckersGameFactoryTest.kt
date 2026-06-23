package edu.austral.dissis.checkers.game

import edu.austral.dissis.common.pieces.Color
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class CheckersGameFactoryTest {

    @Test
    fun `CheckersGameFactory creates a valid game`() {
        val factory = CheckersGameFactory()
        val (game, moveApplier) = factory.createGame()
        
        assertNotNull(game)
        assertNotNull(moveApplier)
        assertEquals(Color.WHITE, game.turnManager.currentTurn)
        assertEquals(8, game.board.size.rows)
        assertEquals(8, game.board.size.columns)
    }
}
