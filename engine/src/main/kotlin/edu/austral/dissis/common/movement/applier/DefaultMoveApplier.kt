package edu.austral.dissis.common.movement.applier

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.common.game.condition.WinCondition
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.effect.PostMoveEffect
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.common.turn.TurnManager
import edu.austral.dissis.common.rules.MoveContext

class DefaultMoveApplier(
    private val moveValidator: MoveValidator,
    private val postMoveEffects: List<PostMoveEffect>,
    private val winConditions: List<WinCondition>,
) : MoveApplier {
    override fun executeMove(
        game: Game,
        vector: MovementVector,
    ): MoveResult {
        val validationResult = moveValidator.validateMove(game.board, game.turnManager.currentTurn, game.moveHistory, vector)
        if (validationResult is RuleResult.Invalid) return MoveResult.Failure("Movimiento invÃ¡lido", game)

        val ruleEffects = (validationResult as RuleResult.Valid).effects
        val finalBoard = processBoardMove(game.board, vector, ruleEffects)
        val nextGameState = buildNextGameState(game, finalBoard, vector)

        return MoveResult.Success(evaluateWinConditions(nextGameState))
    }

    private fun processBoardMove(
        board: Board,
        vector: MovementVector,
        ruleEffects: List<PostMoveEffect>,
    ): Board {
        val movedBoard = board.movePiece(vector)
        val allEffects = ruleEffects + postMoveEffects
        return applyEffects(board, movedBoard, vector, allEffects)
    }

    private fun applyEffects(
        previousBoard: Board,
        movedBoard: Board,
        vector: MovementVector,
        effects: List<PostMoveEffect>,
    ): Board =
        effects.fold(movedBoard) { currentBoard, effect ->
            effect.apply(previousBoard, currentBoard, vector)
        }

    private fun buildNextGameState(
        game: Game,
        finalBoard: Board,
        vector: MovementVector,
    ): Game {
        val pieceToMove = game.board.getPieceAt(vector.origin)
        val moveContext = MoveContext(finalBoard, vector, game.turnManager.currentTurn, game.moveHistory)
        val nextTurnManager = game.turnManager.advance(moveContext)
        val moveRecord = MoveRecord(pieceToMove, vector.origin, vector.destination)
        val nextHistory = game.moveHistory + moveRecord

        return game.copy(
            board = finalBoard,
            turnManager = nextTurnManager,
            moveHistory = nextHistory,
        )
    }

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
