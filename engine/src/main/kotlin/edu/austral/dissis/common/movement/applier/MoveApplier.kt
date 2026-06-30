package edu.austral.dissis.common.movement.applier

import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.common.movement.MovementVector
import edu.austral.dissis.common.movement.Position

interface MoveApplier {
    fun executeMove(
        game: Game,
        vector: MovementVector,
    ): MoveResult
}
