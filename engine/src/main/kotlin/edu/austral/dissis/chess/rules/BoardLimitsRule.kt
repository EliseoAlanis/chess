package edu.austral.dissis.chess.rules

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color

class BoardLimitsRule(
    private val maxRow: Int,
    private val maxColumn: Int,
) : ChessRule {
    override fun isValid(
        board: Board,
        origin: Position,
        destination: Position,
        currentTurn: Color,
    ): Boolean = destination.row in 0..maxRow && destination.col in 0..maxColumn
}
