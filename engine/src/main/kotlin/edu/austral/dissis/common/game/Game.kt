package edu.austral.dissis.common.game

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.rules.BasicRule
import edu.austral.dissis.common.turn.TurnManager
import edu.austral.dissis.common.movement.effect.PostMoveEffect
import edu.austral.dissis.common.game.condition.WinCondition

data class Game(
    val board: Board,
    val turnManager: TurnManager,
    val moveHistory: List<MoveRecord> = emptyList(),
    val status: GameStatus = GameStatus.IN_PROGRESS,
)
