package edu.austral.dissis.common.game

import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.applier.MoveApplier

data class GameEngine(
    val moveApplier: MoveApplier,
    val currentGame: Game,
    val pastGames: List<Game> = emptyList(),
    val futureGames: List<Game> = emptyList(),
) {
    fun applyMove(path: Path): Pair<GameEngine, MoveResult> {
        val result = moveApplier.executeMove(currentGame, path)
        return if (result is MoveResult.Success) {
            val nextEngine =
                this.copy(
                    currentGame = result.newGameState,
                    pastGames = pastGames + currentGame,
                    futureGames = emptyList(),
                )
            Pair(nextEngine, result)
        } else {
            Pair(this, result)
        }
    }

    fun undo(): GameEngine =
        if (canUndo()) {
            this.copy(
                currentGame = pastGames.last(),
                pastGames = pastGames.dropLast(1),
                futureGames = futureGames + currentGame,
            )
        } else {
            this
        }

    fun redo(): GameEngine =
        if (canRedo()) {
            this.copy(
                currentGame = futureGames.last(),
                pastGames = pastGames + currentGame,
                futureGames = futureGames.dropLast(1),
            )
        } else {
            this
        }

    fun canUndo(): Boolean = pastGames.isNotEmpty()

    fun canRedo(): Boolean = futureGames.isNotEmpty()
}
