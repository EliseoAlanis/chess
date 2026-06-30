package edu.austral.dissis.chess.movement.factory

import edu.austral.dissis.chess.movement.atomics.DiagonalMovement
import edu.austral.dissis.chess.movement.atomics.LShapeMovement
import edu.austral.dissis.chess.movement.atomics.StraightMovement
import edu.austral.dissis.chess.movement.composite.CompositeMovement
import edu.austral.dissis.common.movement.Movement

import edu.austral.dissis.common.movement.ClearPathMovement

object MovementFactory {
    fun createRookMovement(): Movement = ClearPathMovement(StraightMovement())

    fun createBishopMovement(): Movement = ClearPathMovement(DiagonalMovement())

    fun createQueenMovement(): Movement = ClearPathMovement(CompositeMovement(listOf(StraightMovement(), DiagonalMovement())))

    fun createKnightMovement(): Movement = LShapeMovement(2, 1) // NO ClearPathMovement so it can jump!

    fun createKingMovement(): Movement = ClearPathMovement(CompositeMovement(listOf(StraightMovement(1), DiagonalMovement(1))))

    fun createChancellorMovement(): Movement = CompositeMovement(listOf(ClearPathMovement(StraightMovement()), LShapeMovement(2, 1)))

    fun createArchbishopMovement(): Movement = CompositeMovement(listOf(ClearPathMovement(DiagonalMovement()), LShapeMovement(2, 1)))
}
