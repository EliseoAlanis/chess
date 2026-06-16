package edu.austral.dissis.chess.game.condition

import edu.austral.dissis.chess.game.Game
import edu.austral.dissis.chess.game.GameStatus

interface WinCondition {
    fun checkStatus(game: Game): GameStatus?
}
