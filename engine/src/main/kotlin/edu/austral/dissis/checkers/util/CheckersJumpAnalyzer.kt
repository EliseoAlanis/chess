package edu.austral.dissis.checkers.util

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color

object CheckersJumpAnalyzer {
    fun hasAnyJumpAvailable(
        board: Board,
        color: Color,
    ): Boolean {
        for ((pos, piece) in board.positions) {
            if (piece.color == color) {
                if (getValidJumps(board, pos).isNotEmpty()) {
                    return true
                }
            }
        }
        return false
    }

    fun getValidJumps(
        board: Board,
        origin: Position,
    ): List<Position> {
        if (!board.hasPieceAt(origin)) return emptyList()
        val piece = board.getPieceAt(origin)
        val jumps = mutableListOf<Position>()

        val directions =
            listOf(
                Pair(2, 2),
                Pair(2, -2),
                Pair(-2, 2),
                Pair(-2, -2),
            )

        for ((rowStep, colStep) in directions) {
            val destRow = origin.row + rowStep
            val destCol = origin.col + colStep
            val destPos = Position(destRow, destCol)

            if (destRow !in 1..board.size.rows || destCol !in 1..board.size.columns) continue

            val vector = MovementVector(origin, destPos)

            if (board.hasPieceAt(destPos)) continue

            val context =
                edu.austral.dissis.common.rules
                    .MoveContext(board, vector, piece.color)
            if (piece.evaluateMove(context, isCapture = true) !is edu.austral.dissis.common.rules.RuleResult.Valid) continue

            val intermediateRow = origin.row + (rowStep / 2)
            val intermediateCol = origin.col + (colStep / 2)
            val intermediatePos = Position(intermediateRow, intermediateCol)

            if (board.hasPieceAt(intermediatePos)) {
                val enemy = board.getPieceAt(intermediatePos)
                if (enemy.color != piece.color) {
                    jumps.add(destPos)
                }
            }
        }

        return jumps
    }
}
