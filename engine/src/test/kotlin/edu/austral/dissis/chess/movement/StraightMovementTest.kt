package edu.austral.dissis.chess.movement

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class StraightMovementTest {
    private val movement = StraightMovement()

    @Test
    fun `move right along same row is valid`() {
        assertTrue(movement.checkTrajectory(Position(3, 0), Position(3, 5)))
    }

    @Test
    fun `move left along same row is valid`() {
        assertTrue(movement.checkTrajectory(Position(3, 5), Position(3, 0)))
    }

    @Test
    fun `move up along same column is valid`() {
        assertTrue(movement.checkTrajectory(Position(0, 3), Position(7, 3)))
    }

    @Test
    fun `move down along same column is valid`() {
        assertTrue(movement.checkTrajectory(Position(7, 3), Position(0, 3)))
    }

    @Test
    fun `diagonal move is not valid`() {
        assertFalse(movement.checkTrajectory(Position(0, 0), Position(3, 3)))
    }

    @Test
    fun `off-axis move is not valid`() {
        assertFalse(movement.checkTrajectory(Position(1, 1), Position(3, 4)))
    }

    @Test
    fun `staying in same position is not valid`() {
        assertFalse(movement.checkTrajectory(Position(4, 4), Position(4, 4)))
    }

    @Test
    fun `single step right is valid`() {
        assertTrue(movement.checkTrajectory(Position(2, 2), Position(2, 3)))
    }

    @Test
    fun `single step up is valid`() {
        assertTrue(movement.checkTrajectory(Position(2, 2), Position(3, 2)))
    }
}
