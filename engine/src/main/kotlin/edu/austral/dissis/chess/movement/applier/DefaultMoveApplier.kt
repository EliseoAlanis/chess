package edu.austral.dissis.chess.movement.applier

import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.GameStatus
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.applier.MoveApplier
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.movement.effect.PostMoveEffect
import edu.austral.dissis.common.game.condition.WinCondition

class DefaultMoveApplier(
    private val moveValidator: MoveValidator,
    private val postMoveEffects: List<PostMoveEffect>,
    private val winConditions: List<WinCondition>
) : MoveApplier {
    override fun executeMove(game: Game, origin: Position, destination: Position): MoveResult {
        if (!moveValidator.canMove(game.board, game.turnManager.currentTurn, origin, destination, game.moveHistory)) {
            return MoveResult.Failure("Movimiento inválido", game)
        }
        val pieceToMove = game.board.getPieceAt(origin)

        var nextBoard = game.board.movePiece(origin, destination)
        
        for (effect in postMoveEffects) {
            nextBoard = effect.apply(game.board, nextBoard, origin, destination)
        }

        val nextTurnManager = game.turnManager.advance()

        val moveRecord = MoveRecord(pieceToMove, origin, destination)
        val nextHistory = game.moveHistory + moveRecord

        var nextGameState = game.copy(board = nextBoard, turnManager = nextTurnManager, moveHistory = nextHistory)

        for (winCondition in winConditions) {
            val newStatus = winCondition.checkStatus(nextGameState)
            if (newStatus != null) {
                nextGameState = nextGameState.copy(status = newStatus)
                break
            }
        }

        return MoveResult.Success(nextGameState)
    }
}
