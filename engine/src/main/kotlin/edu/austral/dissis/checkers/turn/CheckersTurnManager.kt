package edu.austral.dissis.checkers.turn

import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.turn.TurnManager

class CheckersTurnManager(
    override val currentTurn: Color = Color.WHITE
) : TurnManager {
    override fun advance(): TurnManager {
        val nextTurn = if (currentTurn == Color.WHITE) Color.BLACK else Color.WHITE
        return CheckersTurnManager(nextTurn)
    }
}
