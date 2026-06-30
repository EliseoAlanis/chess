package edu.austral.dissis.common.turn

import edu.austral.dissis.common.pieces.Color

import edu.austral.dissis.common.rules.MoveContext

interface TurnManager {
    val currentTurn: Color

    fun advance(context: MoveContext): TurnManager
}
