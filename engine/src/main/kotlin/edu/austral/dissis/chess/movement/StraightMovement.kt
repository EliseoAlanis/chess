package edu.austral.dissis.chess.movement

class StraightMovement : Movement {
    override fun checkTrajectory(
        origin: Position,
        destination: Position,
    ): Boolean {
        val movesInRow = origin.row == destination.row
        val movesInColumn = origin.col == destination.col

        return (movesInRow || movesInColumn) && origin != destination
    }
}
