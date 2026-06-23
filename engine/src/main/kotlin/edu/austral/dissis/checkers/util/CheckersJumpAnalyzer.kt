package edu.austral.dissis.checkers.util

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color

object CheckersJumpAnalyzer {

    fun hasAnyJumpAvailable(board: Board, color: Color): Boolean {
        for ((pos, piece) in board.positions) {
            if (piece.color == color) {
                if (getValidJumps(board, pos).isNotEmpty()) {
                    return true
                }
            }
        }
        return false
    }

    fun getValidJumps(board: Board, origin: Position): List<Position> {
        if (!board.hasPieceAt(origin)) return emptyList()
        val piece = board.getPieceAt(origin)
        val jumps = mutableListOf<Position>()

        // A jump moves 2 squares in row/col. We test all 4 possible diagonal jumps.
        val directions = listOf(
            Pair(2, 2), Pair(2, -2), Pair(-2, 2), Pair(-2, -2)
        )

        for ((rowStep, colStep) in directions) {
            val destRow = origin.row + rowStep
            val destCol = origin.col + colStep
            val destPos = Position(destRow, destCol)

            // Check boundaries
            if (destRow !in 1..board.size.rows || destCol !in 1..board.size.columns) continue

            val path = Path(origin, destPos)
            
            // Destination must be empty
            if (board.hasPieceAt(destPos)) continue

            // Must match capture pattern
            if (!piece.isValidMovePattern(path, isCapture = true)) continue

            // Intermediate must have enemy
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
