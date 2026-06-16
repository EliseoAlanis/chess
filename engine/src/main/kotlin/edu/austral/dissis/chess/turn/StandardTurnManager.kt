package edu.austral.dissis.chess.turn

import edu.austral.dissis.chess.pieces.Color

data class StandardTurnManager(
    override val currentTurn: Color = Color.BLANCO,
) : TurnManager {
    override fun advance(): TurnManager {
        val nextColor = if (currentTurn == Color.BLANCO) Color.NEGRO else Color.BLANCO
        return this.copy(currentTurn = nextColor)
    }
}
