package edu.austral.dissis.common.pieces

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

data class Piece(
    val id: String,
    val color: Color,
    val pieceType: String,
    val movements: Movement,
    val captureMovements: Movement = movements,
) {
    fun evaluateMove(
        context: MoveContext,
        isCapture: Boolean = false,
    ): RuleResult {
        val movementToUse = if (isCapture) captureMovements else movements
        return movementToUse.evaluate(context)
    }
}
