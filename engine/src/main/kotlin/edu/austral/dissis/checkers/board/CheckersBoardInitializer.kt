package edu.austral.dissis.checkers.board

import edu.austral.dissis.checkers.pieces.CheckersPieceFactory
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardInitializer
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece

class CheckersBoardInitializer : BoardInitializer {
    companion object {
        private const val BOARD_SIZE = 8
        private const val WHITE_ROWS_LIMIT = 3
        private const val BLACK_ROWS_START = 6
        private const val EVEN_DIVISOR = 2
    }

    override fun generateInitialPositions(): Map<Position, Piece> {
        val positions = mutableMapOf<Position, Piece>()

        var pieceIdCounter = 1

        for (row in 1..BOARD_SIZE) {
            for (col in 1..BOARD_SIZE) {
                if ((row + col) % EVEN_DIVISOR == 0) {
                    if (row <= WHITE_ROWS_LIMIT) {
                        val piece =
                            CheckersPieceFactory.createMan(
                                id = "W_MAN_${pieceIdCounter++}",
                                color = Color.WHITE,
                            )
                        positions[Position(row, col)] = piece
                    } else if (row >= BLACK_ROWS_START) {
                        val piece =
                            CheckersPieceFactory.createMan(
                                id = "B_MAN_${pieceIdCounter++}",
                                color = Color.BLACK,
                            )
                        positions[Position(row, col)] = piece
                    }
                }
            }
        }

        return positions
    }
}
