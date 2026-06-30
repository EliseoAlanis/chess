package edu.austral.dissis.checkers.movement.validator

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.rules.BasicRule
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult
import kotlin.math.abs

class CheckersMoveValidator(
    private val basicRules: List<BasicRule>
) : MoveValidator {

    override fun validateMove(
        board: Board,
        turn: Color,
        history: List<MoveRecord>,
        path: Path
    ): RuleResult {
        var result: RuleResult = RuleResult.Invalid
        val context = MoveContext(board, path, turn, history)

        if (isBasicValidationPassed(context)) {
            val piece = board.getPieceAt(path.origin)
            val isMultiJumpSequence = isMultiJumpState(history, turn)
            
            if (isMultiJumpSequence && path.origin != history.last().destination) {
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
                val intermediatePos = getIntermediatePosition(path)
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

    override fun canMove(board: Board, turn: Color, path: Path, history: List<MoveRecord>): Boolean {
        return validateMove(board, turn, history, path) is RuleResult.Valid
    }

    private fun isBasicValidationPassed(context: MoveContext): Boolean {
        return context.board.hasPieceAt(context.path.origin) && basicRules.all { rule ->
            rule.evaluate(context) is RuleResult.Valid
        }
    }

    private fun getIntermediatePosition(path: Path): edu.austral.dissis.common.movement.Position {
        val rowDir = if (path.destination.row > path.origin.row) 1 else -1
        val colDir = if (path.destination.col > path.origin.col) 1 else -1
        return edu.austral.dissis.common.movement.Position(
            path.origin.row + rowDir,
            path.origin.col + colDir
        )
    }
}
