package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule

class CorrectTurnRule : BasicRule {
    override fun isValid(
        board: Board,
        path: Path,
        currentTurn: Color,
    ): Boolean {
        val pieceToMove = board.getPieceAt(path.origin)

        return pieceToMove.color == currentTurn
    }
}
