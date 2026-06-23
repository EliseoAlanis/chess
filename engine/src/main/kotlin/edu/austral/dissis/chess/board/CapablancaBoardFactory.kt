package edu.austral.dissis.chess.board

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize

class CapablancaBoardFactory : edu.austral.dissis.common.board.BoardFactory {
    override fun createBoard(): Board {
        val size = BoardSize(10, 8)
        val positions = CapablancaBoardInitializer().generateInitialPositions()
        return Board(positions, size)
    }
}
