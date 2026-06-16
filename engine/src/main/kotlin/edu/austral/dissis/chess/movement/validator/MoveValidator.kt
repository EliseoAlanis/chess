package edu.austral.dissis.chess.movement.validator

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.movement.MoveRecord
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.rules.basic.BasicRule
import edu.austral.dissis.chess.rules.special.SpecialRule

class MoveValidator(
    private val basicRules: List<BasicRule>,
    private val specialRules: List<SpecialRule> = emptyList(),
) {
    fun canMove(
        board: Board,
        turn: Color,
        origin: Position,
        destination: Position,
        history: List<MoveRecord>,
    ): Boolean {
        if (!board.hasPieceAt(origin) || !basicRules.all { rule -> rule.isValid(board, origin, destination, turn) }) {
            return false
        }

        val piece = board.getPieceAt(origin)

        val targetPiece = board.positions[destination]
        if (targetPiece != null && targetPiece.color == turn) {
            return false
        }
        val isCapture = targetPiece != null
        val isStandardMove = piece.isValidMovePattern(origin, destination, isCapture)

        val isSpecialMove =
            specialRules.any { specialRule ->
                specialRule.canExecute(board, origin, destination, history)
            }

        return isStandardMove || isSpecialMove
    }
}
