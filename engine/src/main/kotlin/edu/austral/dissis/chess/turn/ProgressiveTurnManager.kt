package edu.austral.dissis.chess.turn

import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.turn.TurnManager

data class ProgressiveTurnManager(
    override val currentTurn: Color = Color.WHITE,
    private val turnSequence: Int = 1,
    private val movesLeftInTurn: Int = 1,
) : TurnManager {
    override fun advance(): TurnManager =
        if (movesLeftInTurn > 1) {
            ProgressiveTurnManager(
                currentTurn = currentTurn,
                turnSequence = turnSequence,
                movesLeftInTurn = movesLeftInTurn - 1,
            )
        } else {
            val nextColor = if (currentTurn == Color.WHITE) Color.BLACK else Color.WHITE
            val nextSequence = turnSequence + 1

            ProgressiveTurnManager(
                currentTurn = nextColor,
                turnSequence = nextSequence,
                movesLeftInTurn = nextSequence,
            )
        }
}
