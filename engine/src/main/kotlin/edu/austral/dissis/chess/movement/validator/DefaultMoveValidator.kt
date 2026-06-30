package edu.austral.dissis.chess.movement.validator

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.Rule
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

class DefaultMoveValidator(
    private val mandatoryRules: List<Rule>
) : MoveValidator {
    override fun validateMove(
        board: Board,
        turn: Color,
        history: List<MoveRecord>,
        path: Path,
    ): RuleResult {
        val context = MoveContext(board, path, turn, history)
        if (!isBasicValidationPassed(context)) return RuleResult.Invalid
        
        return getStandardMoveResult(context)
    }

    override fun canMove(
        board: Board,
        turn: Color,
        path: Path,
        history: List<MoveRecord>,
    ): Boolean = validateMove(board, turn, history, path) is RuleResult.Valid

    private fun isBasicValidationPassed(context: MoveContext): Boolean =
        context.board.hasPieceAt(context.path.origin) &&
            mandatoryRules.all { rule ->
                rule.evaluate(context) is RuleResult.Valid
            }

    private fun getStandardMoveResult(context: MoveContext): RuleResult {
        val piece = context.board.getPieceAt(context.path.origin)
        val isCapture = context.board.hasPieceAt(context.path.destination)
        return piece.evaluateMove(context, isCapture)
    }
}
