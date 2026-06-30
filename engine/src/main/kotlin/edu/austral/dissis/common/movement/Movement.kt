package edu.austral.dissis.common.movement

import edu.austral.dissis.common.rules.Rule
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sign

interface Movement : Rule {

    fun getIntermediatePositions(path: Path): List<Position> {
        val rowStep = (path.destination.row - path.origin.row).sign
        val colStep = (path.destination.col - path.origin.col).sign

        val steps = max(abs(path.destination.row - path.origin.row), abs(path.destination.col - path.origin.col)) - 1
        if (steps <= 0) return emptyList()

        return (1..steps).map { i ->
            Position(
                path.origin.row + (rowStep * i),
                path.origin.col + (colStep * i)
            )
        }
    }
}
