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
        if (!isBasicValidationPassed(board, origin, destination, turn)) return RuleResult.Invalid
        if (isValidStandardMove(board, origin, destination)) return RuleResult.Valid()
        
        return trySpecialRules(board, origin, destination, history)
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

    private fun isBasicValidationPassed(board: Board, origin: Position, destination: Position, turn: Color): Boolean {
        return board.hasPieceAt(origin) && basicRules.all { rule -> 
            rule.isValid(board, origin, destination, turn) 
        }
    }

    private fun isValidStandardMove(board: Board, origin: Position, destination: Position): Boolean {
        val piece = board.getPieceAt(origin)
        val isCapture = board.hasPieceAt(destination)
        return piece.isValidMovePattern(origin, destination, isCapture)
    }

    private fun trySpecialRules(board: Board, origin: Position, destination: Position, history: List<MoveRecord>): RuleResult {
        for (rule in specialRules) {
            val result = rule.tryExecute(board, origin, destination, history)
            if (result is RuleResult.Valid) {
                return result
            }
        }
        return RuleResult.Invalid
    }
}
