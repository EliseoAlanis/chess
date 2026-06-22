package edu.austral.dissis.common.movement

interface Movement {
    fun checkTrajectory(
        origin: Position,
        destination: Position,
    ): Boolean
}
