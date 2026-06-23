package edu.austral.dissis.common.movement.applier

import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.movement.Path

interface MoveApplier {
    fun executeMove(game: Game, path: Path): MoveResult
}
