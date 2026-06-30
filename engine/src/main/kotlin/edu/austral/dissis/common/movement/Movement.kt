package edu.austral.dissis.common.movement

import edu.austral.dissis.common.rules.Rule
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

interface Movement : Rule {

    fun getIntermediatePositions(vector: MovementVector): List<Position> {
        return vector.getIntermediatePositions()
    }
}
