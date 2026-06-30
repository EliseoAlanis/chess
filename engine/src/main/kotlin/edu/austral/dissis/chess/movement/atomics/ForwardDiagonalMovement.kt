package edu.austral.dissis.chess.movement.atomics

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import kotlin.math.abs
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class ForwardDiagonalMovement(
    private val direction: Int,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val path = context.path
        val isValid = path.destination.row == path.origin.row + direction && abs(path.destination.col - path.origin.col) == 1
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }
}
