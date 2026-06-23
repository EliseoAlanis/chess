package edu.austral.dissis.checkers.board

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardFactory
import edu.austral.dissis.common.board.BoardSize

class CheckersBoardFactory : BoardFactory {
    override fun createBoard(): Board {
        val size = BoardSize(8, 8)
        val positions = CheckersBoardInitializer().generateInitialPositions()
        return Board(positions, size)
    }
}
