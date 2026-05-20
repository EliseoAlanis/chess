package edu.austral.dissis.chess.movement

interface Movement {
    fun checkTrajectory(
        origin: Position,
        destination: Position,
    ): Boolean
}
