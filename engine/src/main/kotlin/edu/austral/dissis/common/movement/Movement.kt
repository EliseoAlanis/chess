package edu.austral.dissis.common.movement

interface Movement {
    fun checkTrajectory(
        path: Path,
    ): Boolean
}
