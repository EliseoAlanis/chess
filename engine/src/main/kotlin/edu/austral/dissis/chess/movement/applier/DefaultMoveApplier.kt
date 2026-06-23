package edu.austral.dissis.chess.movement.applier

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.condition.WinCondition
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.movement.applier.BaseMoveApplier
import edu.austral.dissis.common.movement.effect.PostMoveEffect
import edu.austral.dissis.common.movement.validator.MoveValidator
import edu.austral.dissis.common.turn.TurnManager

class DefaultMoveApplier(
    moveValidator: MoveValidator,
    postMoveEffects: List<PostMoveEffect>,
    winConditions: List<WinCondition>,
) : BaseMoveApplier(moveValidator, postMoveEffects, winConditions) {
    override fun advanceTurnManager(
        game: Game,
        finalBoard: Board,
        path: Path,
    ): TurnManager = game.turnManager.advance()
}
