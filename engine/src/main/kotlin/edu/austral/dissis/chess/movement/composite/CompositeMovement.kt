package edu.austral.dissis.chess.movement.composite

import edu.austral.dissis.common.movement.Movement
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position

import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class CompositeMovement(
    private val movements: List<Movement>,
) : Movement {
    override fun evaluate(context: MoveContext): RuleResult {
        val isValid = movements.any { it.evaluate(context) is RuleResult.Valid }
        return if (isValid) RuleResult.Valid() else RuleResult.Invalid
    }

    override fun getIntermediatePositions(path: Path): List<Position> {
        // We have to mock a MoveContext to check which movement is valid for this path
        // This highlights that getIntermediatePositions is intrinsically linked to the trajectory rule
        val dummyContext = edu.austral.dissis.common.rules.MoveContext(
            edu.austral.dissis.common.board.Board(emptyMap(), edu.austral.dissis.common.board.BoardSize(1,1)), 
            path, 
            edu.austral.dissis.common.pieces.Color.WHITE
        )
        val matchingMovement = movements.find { it.evaluate(dummyContext) is RuleResult.Valid }
        return matchingMovement?.getIntermediatePositions(path) ?: emptyList()
    }
}
