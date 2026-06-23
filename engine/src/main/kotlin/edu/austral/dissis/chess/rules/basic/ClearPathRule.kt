package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule
import kotlin.math.sign

class ClearPathRule : BasicRule {
    override fun isValid(
        board: Board,
        path: Path,
        currentTurn: Color,
    ): Boolean {
        val piece = board.getPieceAt(path.origin)

        val intermediate = piece.movements.getIntermediatePositions(path)

        return intermediate.none { pos -> board.hasPieceAt(pos) }
    }
}
