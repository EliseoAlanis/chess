package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.chess.movement.validator.AttackDetector
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule
import edu.austral.dissis.chess.pieces.PieceType

class NoSelfCheckRule(
    private val attackDetector: AttackDetector,
) : BasicRule {
    override fun isValid(
        board: Board,
        path: Path,
        currentTurn: Color,
    ): Boolean {
        val simulatedBoard = board.movePiece(path)

        val myKingPos = attackDetector.findPiecePosition(simulatedBoard, PieceType.KING, currentTurn) ?: return true

        return !attackDetector.isSquareAttackedByEnemy(simulatedBoard, myKingPos, currentTurn)
    }
}
