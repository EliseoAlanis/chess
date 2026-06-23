package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.rules.BasicRule
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color

class FriendlyFireRule : BasicRule {
    override fun isValid(
        board: Board,
        origin: Position,
        destination: Position,
        currentTurn: Color,
    ): Boolean {
        if (!board.hasPieceAt(destination)) return true
        return board.getPieceAt(destination).color != currentTurn
    }
}

