package edu.austral.dissis.chess.exam

import edu.austral.dissis.chess.test.TestBoard
import edu.austral.dissis.chess.test.TestPosition
import edu.austral.dissis.chess.test.TestSize
import edu.austral.dissis.chess.test.game.TestGameRunner
import edu.austral.dissis.chess.test.game.TestMoveResult
import edu.austral.dissis.chess.test.game.TestMoveSuccess

class DummyTestGameRunner : TestGameRunner {
    override fun executeMove(
        from: TestPosition,
        to: TestPosition,
    ): TestMoveResult = TestMoveSuccess(this)

    override fun getBoard(): TestBoard = TestBoard(TestSize(8, 8), emptyMap())

    override fun redo(): TestMoveResult = TestMoveSuccess(this)

    override fun undo(): TestMoveResult = TestMoveSuccess(this)

    override fun withBoard(board: TestBoard): TestGameRunner = this
}
