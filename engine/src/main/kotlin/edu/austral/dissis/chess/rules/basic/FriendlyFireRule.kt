package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule

class FriendlyFireRule : BasicRule {
    override fun isValid(
        board: Board,
        path: Path,
        currentTurn: Color,
    ): Boolean {
        if (!board.hasPieceAt(path.destination)) return true
        return board.getPieceAt(path.destination).color != currentTurn
    }
}
