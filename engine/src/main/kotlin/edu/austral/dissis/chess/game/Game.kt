package edu.austral.dissis.chess.game

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.MoveRecord
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.rules.ChessRule
import edu.austral.dissis.chess.turn.TurnManager

data class Game(
    val board: Board,
    val rules: List<ChessRule>,
    val turnManager: TurnManager,
    val moveHistory: List<MoveRecord> = emptyList(),
) {
    fun playMove(
        origin: Position,
        destination: Position,
    ): Game {
        val isValidMove = board.hasPieceAt(origin) && areAllRulesValid(origin, destination)

        if (!isValidMove) return this

        val pieceToMove = board.getPieceAt(origin)

        val nextBoard = board.movePiece(origin, destination)

        val nextTurnManager = turnManager.advance()

        val moveRecord = MoveRecord(pieceToMove, origin, destination)
        val nexHistory = moveHistory + moveRecord

        return this.copy(board = nextBoard, turnManager = nextTurnManager, moveHistory = nexHistory)
    }

    private fun areAllRulesValid(
        origin: Position,
        destination: Position,
    ): Boolean {
        val actualColor = turnManager.currentTurn

        return rules.all { rule ->
            rule.isValid(board, origin, destination, actualColor)
        }
    }
}
