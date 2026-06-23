package edu.austral.dissis.common.movement.applier

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.common.game.condition.WinCondition
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.effect.PostMoveEffect
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.common.turn.TurnManager

abstract class BaseMoveApplier(
    protected val moveValidator: MoveValidator,
    protected val postMoveEffects: List<PostMoveEffect>,
    protected val winConditions: List<WinCondition>,
) : MoveApplier {
    override fun executeMove(
        game: Game,
        path: Path,
    ): MoveResult {
        val validationResult = moveValidator.validateMove(game.board, game.turnManager.currentTurn, game.moveHistory, path)
        if (validationResult is RuleResult.Invalid) return MoveResult.Failure("Movimiento inválido", game)

        val ruleEffects = (validationResult as RuleResult.Valid).effects
        val finalBoard = processBoardMove(game.board, path, ruleEffects)
        val nextGameState = buildNextGameState(game, finalBoard, path)

        return MoveResult.Success(evaluateWinConditions(nextGameState))
    }

    private fun processBoardMove(
        board: Board,
        path: Path,
        ruleEffects: List<PostMoveEffect>,
    ): Board {
        val movedBoard = board.movePiece(path)
        val allEffects = ruleEffects + postMoveEffects
        return applyEffects(board, movedBoard, path, allEffects)
    }

    private fun applyEffects(
        previousBoard: Board,
        movedBoard: Board,
        path: Path,
        effects: List<PostMoveEffect>,
    ): Board =
        effects.fold(movedBoard) { currentBoard, effect ->
            effect.apply(previousBoard, currentBoard, path)
        }

    private fun buildNextGameState(
        game: Game,
        finalBoard: Board,
        path: Path,
    ): Game {
        val pieceToMove = game.board.getPieceAt(path.origin)
        val nextTurnManager = advanceTurnManager(game, finalBoard, path)
        val moveRecord = MoveRecord(pieceToMove, path.origin, path.destination)
        val nextHistory = game.moveHistory + moveRecord

        return game.copy(
            board = finalBoard,
            turnManager = nextTurnManager,
            moveHistory = nextHistory,
        )
    }

    protected abstract fun advanceTurnManager(
        game: Game,
        finalBoard: Board,
        path: Path,
    ): TurnManager

    private fun evaluateWinConditions(gameState: Game): Game {
        for (winCondition in winConditions) {
            val newStatus =
                winCondition.checkStatus(
                    board = gameState.board,
                    currentTurn = gameState.turnManager.currentTurn,
                    moveHistory = gameState.moveHistory,
                )
            if (newStatus != edu.austral.dissis.common.game.GameStatus.IN_PROGRESS) {
                return gameState.copy(status = newStatus)
            }
        }
        return gameState
    }
}
