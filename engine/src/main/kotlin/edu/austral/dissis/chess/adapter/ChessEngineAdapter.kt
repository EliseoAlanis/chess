package edu.austral.dissis.chess.adapter

import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.chess.gui.GameInputEvent
import edu.austral.dissis.chess.gui.GameState
import edu.austral.dissis.chess.gui.Move
import edu.austral.dissis.common.movement.applier.MoveApplier
import io.reactivex.rxjava3.core.Observable
import edu.austral.dissis.chess.gui.BoardSize as UIBoardSize

class ChessEngineAdapter(
    private var domainGame: Game,
    private val moveApplier: MoveApplier,
    private var pastGames: MutableList<Game> = mutableListOf(),
    private var futureGames: MutableList<Game> = mutableListOf()
) {
    // Transforma el flujo de eventos de la UI en un flujo de estados del juego
    fun process(inputs: Observable<GameInputEvent>): Observable<GameState> =
        inputs.map { event ->
            when (event) {
                GameInputEvent.Init -> toPlayingState()

                is GameInputEvent.MoveEvent -> handleMove(event.move)

                GameInputEvent.Undo -> handleUndo()

                GameInputEvent.Redo -> handleRedo()
            }
        }

    private fun handleMove(move: Move): GameState {
        val origin = move.from.toDomainPosition()
        val dest = move.to.toDomainPosition()

        return when (val result = moveApplier.executeMove(domainGame, origin, dest)) {
            is MoveResult.Success -> handleSuccess(result)
            is MoveResult.Failure -> GameState.InvalidMove(result.reason)
        }
    }

    private fun handleSuccess(result: MoveResult.Success): GameState {
        pastGames.add(domainGame)
        futureGames.clear()
        domainGame = result.newGameState
        return checkForGameOver(domainGame.status) ?: toPlayingState()
    }

    private fun checkForGameOver(status: edu.austral.dissis.common.game.GameStatus): GameState? {
        return when (status) {
            edu.austral.dissis.common.game.GameStatus.WHITE_WINS -> GameState.GameOver(edu.austral.dissis.chess.gui.PlayerColor.WHITE)
            edu.austral.dissis.common.game.GameStatus.BLACK_WINS -> GameState.GameOver(edu.austral.dissis.chess.gui.PlayerColor.BLACK)
            else -> null
        }
    }

    private fun handleUndo(): GameState {
        if (pastGames.isNotEmpty()) {
            futureGames.add(domainGame)
            domainGame = pastGames.removeAt(pastGames.size - 1)
        }
        return toPlayingState()
    }

    private fun handleRedo(): GameState {
        if (futureGames.isNotEmpty()) {
            pastGames.add(domainGame)
            domainGame = futureGames.removeAt(futureGames.size - 1)
        }
        return toPlayingState()
    }

    private fun toPlayingState(): GameState.Playing =
        GameState.Playing(
            boardSize = UIBoardSize(domainGame.board.getSize().columns, domainGame.board.getSize().rows),
            pieces = domainGame.board.toUIPieces(),
            currentPlayer = domainGame.turnManager.currentTurn.toUIColor(),
            canUndo = pastGames.isNotEmpty(),
            canRedo = futureGames.isNotEmpty(),
        )
}
