package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color

class PieceMovementRule : BasicRule {
    override fun isValid(
        board: Board,
        origin: Position,
        destination: Position,
        currentTurn: Color,
    ): Boolean {
        val pieceToMove = board.getPieceAt(origin)

        return pieceToMove.isValidMovePattern(origin, destination)
    }
}
