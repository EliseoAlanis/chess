package edu.austral.dissis.chess.game

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.MoveRecord
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.movement.validator.MoveValidator
import edu.austral.dissis.chess.rules.basic.BasicRule
import edu.austral.dissis.chess.turn.TurnManager
import edu.austral.dissis.chess.movement.effect.PostMoveEffect
import edu.austral.dissis.chess.game.condition.WinCondition

data class Game(
    val board: Board,
    val rules: List<BasicRule>,
    val turnManager: TurnManager,
    val moveHistory: List<MoveRecord> = emptyList(),
    val moveValidator: MoveValidator,
    val postMoveEffects: List<PostMoveEffect> = emptyList(),
    val winConditions: List<WinCondition> = emptyList(),
    val status: GameStatus = GameStatus.IN_PROGRESS,
) {
    fun playMove(
        origin: Position,
        destination: Position,
    ): MoveResult {
        if (!moveValidator.canMove(board, turnManager.currentTurn, origin, destination, moveHistory)) {
            return MoveResult.Failure("Movimiento inválido según las reglas.", this)
        }
        val pieceToMove = board.getPieceAt(origin)

        var nextBoard = board.movePiece(origin, destination)
        
        for (effect in postMoveEffects) {
            nextBoard = effect.apply(board, nextBoard, origin, destination)
        }

        val nextTurnManager = turnManager.advance()

        val moveRecord = MoveRecord(pieceToMove, origin, destination)
        val nextHistory = moveHistory + moveRecord

        var nextGameState = this.copy(board = nextBoard, turnManager = nextTurnManager, moveHistory = nextHistory)

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
