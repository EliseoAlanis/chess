package edu.austral.dissis.checkers.movement.validator

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.Rule
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import kotlin.math.abs

class CheckersMoveValidator(
    private val mandatoryRules: List<Rule>
) : MoveValidator {

    override fun validateMove(
        board: Board,
        turn: Color,
        history: List<MoveRecord>,
        vector: MovementVector
    ): RuleResult {
        var result: RuleResult = RuleResult.Invalid
        val context = MoveContext(board, vector, turn, history)

        if (isBasicValidationPassed(context)) {
            val piece = board.getPieceAt(vector.origin)
            val isMultiJumpSequence = isMultiJumpState(history, turn)
            
            if (isMultiJumpSequence && vector.origin != history.last().destination) {
                return RuleResult.Invalid
            }

            // 1. Regular 1-step move
            if (piece.evaluateMove(context, isCapture = false) is RuleResult.Valid) {
                val hasJumps = edu.austral.dissis.checkers.util.CheckersJumpAnalyzer.hasAnyJumpAvailable(board, turn)
                if (!isMultiJumpSequence && !hasJumps) {
                    result = RuleResult.Valid()
                }
            }
            // 2. Jump move (2 steps)
            else if (piece.evaluateMove(context, isCapture = true) is RuleResult.Valid) {
                val intermediatePos = getIntermediatePosition(vector)
                if (board.hasPieceAt(intermediatePos)) {
                    val enemyPiece = board.getPieceAt(intermediatePos)
                    if (enemyPiece.color != piece.color) {
                        val captureEffect = edu.austral.dissis.checkers.movement.effect.CheckersJumpCaptureEffect(intermediatePos)
                        result = RuleResult.Valid(listOf(captureEffect))
                    }
                }
            }
        }

        return result
    }

    private fun isMultiJumpState(history: List<MoveRecord>, currentTurn: Color): Boolean {
        val lastMove = history.lastOrNull() ?: return false
        return lastMove.piece.color == currentTurn
    }

    override fun canMove(board: Board, turn: Color, vector: MovementVector, history: List<MoveRecord>): Boolean {
        return validateMove(board, turn, history, vector) is RuleResult.Valid
    }

    private fun isBasicValidationPassed(context: MoveContext): Boolean {
        return context.board.hasPieceAt(context.vector.origin) && mandatoryRules.all { rule ->
            rule.evaluate(context) is RuleResult.Valid
        }
    }

    private fun getIntermediatePosition(vector: MovementVector): edu.austral.dissis.common.movement.Position {
        val rowDir = if (vector.destination.row > vector.origin.row) 1 else -1
        val colDir = if (vector.destination.col > vector.origin.col) 1 else -1
        return edu.austral.dissis.common.movement.Position(
            vector.origin.row + rowDir,
            vector.origin.col + colDir
        )
    }
}
