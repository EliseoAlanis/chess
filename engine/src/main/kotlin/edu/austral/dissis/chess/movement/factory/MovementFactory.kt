package edu.austral.dissis.chess.movement.factory

import edu.austral.dissis.chess.movement.atomics.DiagonalMovement
import edu.austral.dissis.chess.movement.atomics.LShapeMovement
import edu.austral.dissis.chess.movement.atomics.StraightMovement
import edu.austral.dissis.chess.movement.composite.CompositeMovement
import edu.austral.dissis.common.movement.Movement

object MovementFactory {
    fun createRookMovement(): Movement = StraightMovement()

    fun createBishopMovement(): Movement = DiagonalMovement()

    fun createQueenMovement(): Movement =
        CompositeMovement(
            listOf(StraightMovement(), DiagonalMovement()),
        )

    fun createKnightMovement(): Movement = LShapeMovement(2, 1)

    fun createKingMovement(): Movement = CompositeMovement(listOf(StraightMovement(1), DiagonalMovement(1)))

    fun createChancellorMovement(): Movement = CompositeMovement(listOf(StraightMovement(), LShapeMovement(2, 1)))

    fun createArchbishopMovement(): Movement = CompositeMovement(listOf(DiagonalMovement(), LShapeMovement(2, 1)))
}
