package edu.austral.dissis.chess.rules

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position
import kotlin.math.sign

class ClearPathRule : ChessRule {
    override fun isValid(
        board: Board,
        origin: Position,
        destination: Position,
        currentTurn: edu.austral.dissis.chess.pieces.Color,
    ): Boolean {
        val piece = board.getPieceAt(origin)

        if (piece.canJump) return true

        val path = getIntermediatePositions(origin, destination)

        return path.none { pos -> board.hasPieceAt(pos) }
    }

    private fun getIntermediatePositions(
        origin: Position,
        dest: Position,
    ): List<Position> {
        val rowStep = (dest.row - origin.row).sign
        val colStep = (dest.col - origin.col).sign

        val startPos = Position(origin.row + rowStep, origin.col + colStep)

        return generateSequence(startPos) { pos ->
            Position(pos.row + rowStep, pos.col + colStep)
        }.takeWhile { it != dest }.toList()
    }
}
