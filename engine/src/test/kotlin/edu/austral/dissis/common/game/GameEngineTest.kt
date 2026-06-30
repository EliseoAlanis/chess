package edu.austral.dissis.common.game

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.applier.MoveApplier
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.turn.TurnManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class GameEngineTest {
    private class MockTurnManager(
        override val currentTurn: Color = Color.WHITE,
    ) : TurnManager {
        override fun advance(context: MoveContext): TurnManager =
            MockTurnManager(
                if (currentTurn ==
                    Color.WHITE
                ) {
                    Color.BLACK
                } else {
                    Color.WHITE
                },
            )
    }

    private class MockMoveApplier(
        private val shouldSucceed: Boolean,
    ) : MoveApplier {
        override fun executeMove(
            game: Game,
            vector: MovementVector,
        ): MoveResult =
            if (shouldSucceed) {
                // In a successful move, we would return a new Game state.
                // For testing, just return a copy of the game to simulate state change.
                val context = MoveContext(game.board, vector, game.turnManager.currentTurn)
                val nextGame = game.copy(turnManager = game.turnManager.advance(context))
                MoveResult.Success(nextGame)
            } else {
                MoveResult.Failure("Mock failure", game)
            }
    }

    private fun createEngine(shouldSucceed: Boolean = true): GameEngine {
        val board = Board(emptyMap(), BoardSize(8, 8))
        val game = Game(board, MockTurnManager())
        return GameEngine(MockMoveApplier(shouldSucceed), game)
    }

    @Test
    fun `applyMove returns new GameEngine and Success when move is valid`() {
        val engine = createEngine(shouldSucceed = true)
        val vector = MovementVector(Position(1, 1), Position(2, 2))

        val (nextEngine, result) = engine.applyMove(vector)

        assertTrue(result is MoveResult.Success)
        assertEquals(Color.BLACK, nextEngine.currentGame.turnManager.currentTurn)
        assertTrue(nextEngine.canUndo())
        assertFalse(nextEngine.canRedo())

        // Original engine is unmodified (Immutable check)
        assertEquals(Color.WHITE, engine.currentGame.turnManager.currentTurn)
        assertFalse(engine.canUndo())
    }

    @Test
    fun `applyMove returns same GameEngine and Failure when move is invalid`() {
        val engine = createEngine(shouldSucceed = false)
        val vector = MovementVector(Position(1, 1), Position(2, 2))

        val (nextEngine, result) = engine.applyMove(vector)

        assertTrue(result is MoveResult.Failure)
        assertEquals(Color.WHITE, nextEngine.currentGame.turnManager.currentTurn)
        assertFalse(nextEngine.canUndo())
    }

    @Test
    fun `undo restores previous state`() {
        val engine = createEngine(shouldSucceed = true)
        val vector = MovementVector(Position(1, 1), Position(2, 2))

        val (engineAfterMove, _) = engine.applyMove(vector)
        assertTrue(engineAfterMove.canUndo())
        assertEquals(Color.BLACK, engineAfterMove.currentGame.turnManager.currentTurn)

        val restoredEngine = engineAfterMove.undo()

        assertEquals(Color.WHITE, restoredEngine.currentGame.turnManager.currentTurn)
        assertFalse(restoredEngine.canUndo())
        assertTrue(restoredEngine.canRedo())
    }

    @Test
    fun `redo reapplies next state`() {
        val engine = createEngine(shouldSucceed = true)
        val vector = MovementVector(Position(1, 1), Position(2, 2))

        val (engineAfterMove, _) = engine.applyMove(vector)
        val undoneEngine = engineAfterMove.undo()

        val redoneEngine = undoneEngine.redo()

        assertEquals(Color.BLACK, redoneEngine.currentGame.turnManager.currentTurn)
        assertTrue(redoneEngine.canUndo())
        assertFalse(redoneEngine.canRedo())
    }
}
