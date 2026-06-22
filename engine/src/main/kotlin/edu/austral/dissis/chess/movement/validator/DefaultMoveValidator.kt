package edu.austral.dissis.chess.movement.validator

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
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
        origin: Position,
        destination: Position,
        history: List<MoveRecord>,
    ): RuleResult {
        if (!board.hasPieceAt(origin) || !basicRules.all { rule -> rule.isValid(board, origin, destination, turn) }) {
            return RuleResult.Invalid
        }

        val piece = board.getPieceAt(origin)
        val targetPiece = board.positions[destination]

        val isCapture = targetPiece != null
        if (piece.isValidMovePattern(origin, destination, isCapture)) {
            return RuleResult.Valid()
        }

        for (rule in specialRules) {
            if (rule.canExecute(board, origin, destination, history)) {
                return RuleResult.Valid()
            }
        }

        return RuleResult.Invalid
    }

    override fun canMove(
        board: Board,
        turn: Color,
        origin: Position,
        destination: Position,
        history: List<MoveRecord>,
    ): Boolean {
        return validateMove(board, turn, origin, destination, history) is RuleResult.Valid
    }
}
