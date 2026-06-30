package edu.austral.dissis.chess.movement.validator

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import edu.austral.dissis.common.rules.SpecialRule

class DefaultMoveValidator(
    private val basicRules: List<BasicRule>,
    private val specialRules: List<SpecialRule> = emptyList(),
) : MoveValidator {
    override fun validateMove(
        board: Board,
        turn: Color,
        history: List<MoveRecord>,
        path: Path,
    ): RuleResult {
        val context = MoveContext(board, path, turn, history)
        if (!isBasicValidationPassed(context)) return RuleResult.Invalid
        if (isValidStandardMove(context)) return RuleResult.Valid()

        return trySpecialRules(context)
    }

    override fun canMove(
        board: Board,
        turn: Color,
        path: Path,
        history: List<MoveRecord>,
    ): Boolean = validateMove(board, turn, history, path) is RuleResult.Valid

    private fun isBasicValidationPassed(context: MoveContext): Boolean =
        context.board.hasPieceAt(context.path.origin) &&
            basicRules.all { rule ->
                rule.evaluate(context) is RuleResult.Valid
            }

    private fun isValidStandardMove(context: MoveContext): Boolean {
        val piece = context.board.getPieceAt(context.path.origin)
        val isCapture = context.board.hasPieceAt(context.path.destination)
        return piece.evaluateMove(context, isCapture) is RuleResult.Valid
    }

    private fun trySpecialRules(context: MoveContext): RuleResult {
        for (rule in specialRules) {
            val result = rule.evaluate(context)
            if (result is RuleResult.Valid) {
                return result
            }
        }
        return RuleResult.Invalid
    }
}
