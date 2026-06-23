package edu.austral.dissis.common.game

import edu.austral.dissis.common.movement.applier.MoveApplier

interface GameFactory {
    fun createGame(): Pair<Game, MoveApplier>
}
