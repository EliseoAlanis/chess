package edu.austral.dissis.chess.movement.effect

import edu.austral.dissis.common.movement.effect.PostMoveEffect
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path

class CastlingEffect(
    private val rookOrigin: Position,
    private val rookDestination: Position
) : PostMoveEffect {
    override fun apply(
        previousBoard: Board,
        currentBoard: Board,
        path: Path,
    ): Board {
        if (!currentBoard.hasPieceAt(rookOrigin)) return currentBoard
        val rook = currentBoard.getPieceAt(rookOrigin)
        val newPositions = currentBoard.positions - rookOrigin + (rookDestination to rook)
        return currentBoard.copy(positions = newPositions)
    }
}
