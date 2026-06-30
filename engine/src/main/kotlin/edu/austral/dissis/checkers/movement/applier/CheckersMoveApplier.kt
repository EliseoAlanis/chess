package edu.austral.dissis.checkers.movement.applier

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.condition.WinCondition
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.applier.BaseMoveApplier
import edu.austral.dissis.common.movement.effect.PostMoveEffect
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.turn.TurnManager
import kotlin.math.abs

class CheckersMoveApplier(
    moveValidator: MoveValidator,
    postMoveEffects: List<PostMoveEffect>,
    winConditions: List<WinCondition>,
) : BaseMoveApplier(moveValidator, postMoveEffects, winConditions)
