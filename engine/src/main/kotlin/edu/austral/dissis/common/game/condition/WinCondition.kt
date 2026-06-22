package edu.austral.dissis.common.game.condition

import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.GameStatus

interface WinCondition {
    fun checkStatus(game: Game): GameStatus?
}
