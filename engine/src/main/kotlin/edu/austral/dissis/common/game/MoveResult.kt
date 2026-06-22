package edu.austral.dissis.common.game

sealed interface MoveResult {
    data class Success(
        val newGameState: Game,
    ) : MoveResult

    data class Failure(
        val reason: String,
        val previousState: Game,
    ) : MoveResult
}
