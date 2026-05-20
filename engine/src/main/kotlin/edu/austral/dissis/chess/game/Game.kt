package edu.austral.dissis.chess.game

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.rules.ChessRule

data class Game(
    val board: Board,
    val currentTurn: Color,
    val rules: List<ChessRule>,
) {
    fun playMove(
        origin: Position,
        destination: Position,
    ): Game {
        val isValidMove =
            board.hasPieceAt(origin)
                    && areAllRulesValid(origin, destination)

        if (!isValidMove) return this

        val nextBoard = board.movePiece(origin, destination)
        val nextTurn = if (currentTurn == Color.BLANCO) Color.NEGRO else Color.BLANCO

        return this.copy(board = nextBoard, currentTurn = nextTurn)
    }

    private fun areAllRulesValid(
        origin: Position,
        destination: Position
    ): Boolean = rules.all { rule ->
        rule.isValid(board, origin, destination, currentTurn)
    }
}
