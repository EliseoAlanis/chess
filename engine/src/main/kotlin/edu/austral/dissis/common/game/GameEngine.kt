package edu.austral.dissis.common.game

import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.applier.MoveApplier

class GameEngine(
    private val moveApplier: MoveApplier,
    var currentGame: Game,
    private val pastGames: MutableList<Game> = mutableListOf(),
    private val futureGames: MutableList<Game> = mutableListOf()
) {
    fun applyMove(path: Path): MoveResult {
        val result = moveApplier.executeMove(currentGame, path)
        if (result is MoveResult.Success) {
            pastGames.add(currentGame)
            futureGames.clear()
            currentGame = result.newGameState
        }
        return result
    }

    fun undo() {
        if (pastGames.isNotEmpty()) {
            futureGames.add(currentGame)
            currentGame = pastGames.removeAt(pastGames.size - 1)
        }
    }

    fun redo() {
        if (futureGames.isNotEmpty()) {
            pastGames.add(currentGame)
            currentGame = futureGames.removeAt(futureGames.size - 1)
        }
    }

    fun canUndo(): Boolean = pastGames.isNotEmpty()
    
    fun canRedo(): Boolean = futureGames.isNotEmpty()
}
