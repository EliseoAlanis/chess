package edu.austral.dissis.chess.turn

import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.turn.TurnManager

data class StandardTurnManager(
    override val currentTurn: Color = Color.WHITE,
) : TurnManager {
    override fun advance(context: MoveContext): TurnManager {
        val nextColor = if (currentTurn == Color.WHITE) Color.BLACK else Color.WHITE
        return this.copy(currentTurn = nextColor)
    }
}
