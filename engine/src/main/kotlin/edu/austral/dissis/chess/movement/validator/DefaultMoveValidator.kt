package edu.austral.dissis.chess.movement.validator

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule
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
        if (!isBasicValidationPassed(board, path, turn)) return RuleResult.Invalid
        if (isValidStandardMove(board, path)) return RuleResult.Valid()

        return trySpecialRules(board, path, history)
    }

    override fun canMove(
        board: Board,
        turn: Color,
        path: Path,
        history: List<MoveRecord>,
    ): Boolean = validateMove(board, turn, history, path) is RuleResult.Valid

    private fun isBasicValidationPassed(
        board: Board,
        path: Path,
        turn: Color,
    ): Boolean =
        board.hasPieceAt(path.origin) &&
            basicRules.all { rule ->
                rule.isValid(board, path, turn)
            }

    private fun isValidStandardMove(
        board: Board,
        path: Path,
    ): Boolean {
        val piece = board.getPieceAt(path.origin)
        val isCapture = board.hasPieceAt(path.destination)
        return piece.isValidMovePattern(path, isCapture)
    }

    private fun trySpecialRules(
        board: Board,
        path: Path,
        history: List<MoveRecord>,
    ): RuleResult {
        for (rule in specialRules) {
            val result = rule.tryExecute(board, path, history)
            if (result is RuleResult.Valid) {
                return result
            }
        }
        return RuleResult.Invalid
    }
}
