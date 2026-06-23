package edu.austral.dissis.chess.board

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize

class ClassicBoardFactory : edu.austral.dissis.common.board.BoardFactory {
    override fun createBoard(): Board {
        val size = BoardSize(8, 8)
        val positions = ClassicBoardInitializer().generateInitialPositions()
        return Board(positions, size)
    }
}
