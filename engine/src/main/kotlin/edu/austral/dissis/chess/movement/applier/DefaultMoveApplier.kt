package edu.austral.dissis.chess.movement.applier

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.GameStatus
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.applier.MoveApplier
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.movement.effect.PostMoveEffect
import edu.austral.dissis.common.game.condition.WinCondition
import edu.austral.dissis.common.rules.RuleResult

class DefaultMoveApplier(
    private val moveValidator: MoveValidator,
    private val postMoveEffects: List<PostMoveEffect>,
    private val winConditions: List<WinCondition>
) : MoveApplier {

    override fun executeMove(game: Game, origin: Position, destination: Position): MoveResult {
        val validationResult = moveValidator.validateMove(game.board, game.turnManager.currentTurn, origin, destination, game.moveHistory)
        if (validationResult is RuleResult.Invalid) return MoveResult.Failure("Movimiento inválido", game)

        val ruleEffects = (validationResult as RuleResult.Valid).effects
        val finalBoard = processBoardMove(game.board, origin, destination, ruleEffects)
        val nextGameState = buildNextGameState(game, finalBoard, origin, destination)

        return MoveResult.Success(evaluateWinConditions(nextGameState))
    }

    private fun processBoardMove(board: Board, origin: Position, destination: Position, ruleEffects: List<PostMoveEffect>): Board {
        val movedBoard = board.movePiece(origin, destination)
        val allEffects = ruleEffects + postMoveEffects
        return applyEffects(board, movedBoard, origin, destination, allEffects)
    }

    private fun applyEffects(previousBoard: Board, movedBoard: Board, origin: Position, destination: Position, effects: List<PostMoveEffect>): Board {
        return effects.fold(movedBoard) { currentBoard, effect ->
            effect.apply(previousBoard, currentBoard, origin, destination)
        }
    }

    private fun buildNextGameState(game: Game, finalBoard: Board, origin: Position, destination: Position): Game {
        val pieceToMove = game.board.getPieceAt(origin)
        val nextTurnManager = game.turnManager.advance()
        val moveRecord = MoveRecord(pieceToMove, origin, destination)
        val nextHistory = game.moveHistory + moveRecord

        return game.copy(
            board = finalBoard,
            turnManager = nextTurnManager,
            moveHistory = nextHistory
        )
    }

    private fun evaluateWinConditions(gameState: Game): Game {
        for (winCondition in winConditions) {
            val newStatus = winCondition.checkStatus(gameState)
            if (newStatus != null) {
                return gameState.copy(status = newStatus)
            }
        }
        return gameState
    }
}
