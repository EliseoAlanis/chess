package edu.austral.dissis.checkers.turn

import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.turn.TurnManager
import edu.austral.dissis.common.rules.MoveContext
import kotlin.math.abs

class CheckersTurnManager(
    override val currentTurn: Color = Color.WHITE,
) : TurnManager {
    override fun advance(context: MoveContext): TurnManager {
        val isJump = abs(context.vector.destination.row - context.vector.origin.row) == 2
        val canJumpAgain =
            isJump &&
                edu.austral.dissis.checkers.util.CheckersJumpAnalyzer
                    .getValidJumps(context.board, context.vector.destination)
                    .isNotEmpty()

        if (canJumpAgain) {
            return this
        }

        val nextTurn = if (currentTurn == Color.WHITE) Color.BLACK else Color.WHITE
        return CheckersTurnManager(nextTurn)
    }
}
