package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.rules.BasicRule

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color
import kotlin.math.sign

class ClearPathRule : BasicRule {
    override fun isValid(
        board: Board,
        path: Path,
        currentTurn: Color,
    ): Boolean {
        val piece = board.getPieceAt(path.origin)

        if (piece.canJump) return true

        val intermediate = getIntermediatePositions(path.origin, path.destination, board)

        return intermediate.none { pos -> board.hasPieceAt(pos) }
    }

    private fun getIntermediatePositions(
        origin: Position,
        dest: Position,
        board: Board,
    ): List<Position> {
        val rowStep = (dest.row - origin.row).sign
        val colStep = (dest.col - origin.col).sign

        val startPos = Position(origin.row + rowStep, origin.col + colStep)

        val beginning = 1
        val end = maxOf(board.size.rows, board.size.columns)

        return generateSequence(startPos) { pos ->
            Position(pos.row + rowStep, pos.col + colStep)
        }.takeWhile { it != dest && it.row in beginning..end && it.col in beginning..end }.toList()
    }
}
