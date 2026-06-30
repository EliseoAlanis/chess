package edu.austral.dissis.common.movement

data class Position(
    val row: Int,
    val col: Int,
) {
    operator fun minus(other: Position): MovementVector = MovementVector(origin = other, destination = this)
}
