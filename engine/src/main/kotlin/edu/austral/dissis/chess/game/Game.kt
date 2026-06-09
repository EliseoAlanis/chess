package edu.austral.dissis.chess.game

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.MoveRecord
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.movement.validator.MoveValidator
import edu.austral.dissis.chess.rules.basic.BasicRule
import edu.austral.dissis.chess.turn.TurnManager

data class Game(
    val board: Board,
    val rules: List<BasicRule>,
    val turnManager: TurnManager,
    val moveHistory: List<MoveRecord> = emptyList(),
    val moveValidator: MoveValidator,
) {
    fun playMove(
        origin: Position,
        destination: Position,
    ): MoveResult {
        if (!moveValidator.canMove(board, turnManager.currentTurn, origin, destination, moveHistory)) {
            return MoveResult.Failure("Movimiento inválido según las reglas.", this)
        }
        val pieceToMove = board.getPieceAt(origin)

        val nextBoard = board.movePiece(origin, destination)

        val nextTurnManager = turnManager.advance()

        val moveRecord = MoveRecord(pieceToMove, origin, destination)
        val nextHistory = moveHistory + moveRecord

        val nextGameState = this.copy(board = nextBoard, turnManager = nextTurnManager, moveHistory = nextHistory)

        return MoveResult.Success(nextGameState)
    }
}
