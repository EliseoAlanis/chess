package edu.austral.dissis.chess.game.condition

import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.common.game.condition.WinCondition

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord

import edu.austral.dissis.common.game.GameStatus
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.validator.AttackDetector
import edu.austral.dissis.common.pieces.Color

import edu.austral.dissis.common.movement.validator.MoveValidator

class ExtinctionCondition : WinCondition {

    private val expectedPieceTypes = setOf(
        PieceType.PAWN,
        PieceType.ROOK,
        PieceType.KNIGHT,
        PieceType.BISHOP,
        PieceType.QUEEN,
        PieceType.KING
    )

    override fun checkStatus(
        board: Board,
        currentTurn: Color,
        moveHistory: List<MoveRecord>
    ): GameStatus {

        // 1. Agarramos todos los tipos de piezas que le quedan vivas a currentTurn
        val aliveTypes = board.positions.values
            .filter { it.color == currentTurn }
            .map { it.pieceType }
            .toSet()

        val isExtinct = !aliveTypes.containsAll(expectedPieceTypes)

        if (isExtinct) {
            return if (currentTurn == Color.WHITE) GameStatus.BLACK_WINS else GameStatus.WHITE_WINS
        }

        return GameStatus.IN_PROGRESS
    }
}
