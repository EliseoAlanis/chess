package edu.austral.dissis.common.turn

import edu.austral.dissis.common.pieces.Color

interface TurnManager {
    val currentTurn: Color

    fun advance(): TurnManager
}
