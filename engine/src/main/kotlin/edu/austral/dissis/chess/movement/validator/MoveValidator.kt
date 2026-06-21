package edu.austral.dissis.chess.movement.validator

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.MoveRecord
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.rules.basic.BasicRule
import edu.austral.dissis.chess.rules.special.RuleResult
import edu.austral.dissis.chess.rules.special.SpecialRule

class MoveValidator(
    private val basicRules: List<BasicRule>,
    private val specialRules: List<SpecialRule> = emptyList(),
) {
    fun validateMove(
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
            val result = rule.evaluate(board, origin, destination, history)
            if (result is RuleResult.Valid) {
                return result
            }
        }

        return RuleResult.Invalid
    }

    fun canMove(
        board: Board,
        turn: Color,
        origin: Position,
        destination: Position,
        history: List<MoveRecord>,
    ): Boolean {
        return validateMove(board, turn, origin, destination, history) is RuleResult.Valid
    }
}
