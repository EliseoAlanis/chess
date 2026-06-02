package edu.austral.dissis.chess.turn

import edu.austral.dissis.chess.pieces.Color

interface TurnManager {
    val currentTurn: Color

    fun advance(): TurnManager
}
