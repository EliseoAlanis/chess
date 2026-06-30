package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import kotlin.math.abs

class ForwardMovement(
    private val direction: Int,
    private val maxSteps: Int = Int.MAX_VALUE,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val path = context.path
        val movesForward = (path.destination.row - path.origin.row) * direction > 0
        val staysInColumn = path.origin.col == path.destination.col
        val steps = abs(path.destination.row - path.origin.row)
        val isValid = movesForward && staysInColumn && steps <= maxSteps
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
