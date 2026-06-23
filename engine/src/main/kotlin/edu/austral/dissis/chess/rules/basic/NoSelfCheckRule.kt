package edu.austral.dissis.chess.rules.basic

import edu.austral.dissis.common.rules.BasicRule
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.validator.AttackDetector
import edu.austral.dissis.common.pieces.Color

class NoSelfCheckRule(
    private val attackDetector: AttackDetector
) : BasicRule {

    override fun isValid(
        board: Board,
        origin: Position,
        destination: Position,
        currentTurn: Color,
    ): Boolean {
        val simulatedBoard = board.movePiece(origin, destination)

        val myKingPos = attackDetector.findKingPosition(simulatedBoard, currentTurn) ?: return true

        return !attackDetector.isSquareAttackedByEnemy(simulatedBoard, myKingPos, currentTurn)
    }
}



