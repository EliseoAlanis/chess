package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import kotlin.math.sign

class ClearPathRule : BasicRule {
    override fun isValid(
        board: Board,
        origin: Position,
        destination: Position,
        currentTurn: Color,
    ): Boolean {
        val piece = board.getPieceAt(origin)

        if (piece.canJump) return true

        val path = getIntermediatePositions(origin, destination, board)

        return path.none { pos -> board.hasPieceAt(pos) }
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
        val end = board.size.rows / board.size.columns

        return generateSequence(startPos) { pos ->
            Position(pos.row + rowStep, pos.col + colStep)
        }.takeWhile { it != dest && it.row in beginning..end && it.col in beginning..end }.toList()
    }
}
