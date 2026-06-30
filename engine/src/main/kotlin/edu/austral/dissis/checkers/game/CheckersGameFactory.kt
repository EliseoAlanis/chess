package edu.austral.dissis.checkers.game

import edu.austral.dissis.checkers.board.CheckersBoardInitializer
import edu.austral.dissis.checkers.game.condition.CheckersExtinctionCondition
import edu.austral.dissis.checkers.movement.applier.CheckersMoveApplier
import edu.austral.dissis.checkers.movement.effect.CheckersPromotionEffect
import edu.austral.dissis.checkers.movement.validator.CheckersMoveValidator
import edu.austral.dissis.checkers.rules.basic.BoardLimitsRule
import edu.austral.dissis.checkers.rules.basic.CorrectTurnRule
import edu.austral.dissis.checkers.rules.basic.EmptyDestinationRule
import edu.austral.dissis.checkers.turn.CheckersTurnManager
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.GameFactory
import edu.austral.dissis.common.movement.applier.MoveApplier

class CheckersGameFactory : GameFactory {
    override fun createGame(): Pair<Game, MoveApplier> {
        val boardFactory =
            edu.austral.dissis.checkers.board
                .CheckersBoardFactory()
        val board = boardFactory.createBoard()
        val turnManager = CheckersTurnManager()

        val mandatoryRules =
            listOf(
                CorrectTurnRule(),
                BoardLimitsRule(),
                EmptyDestinationRule(),
            )

        val moveValidator = CheckersMoveValidator(mandatoryRules)

        val postMoveEffects =
            listOf(
                CheckersPromotionEffect(),
            )

        val winConditions =
            listOf(
                CheckersExtinctionCondition(),
            )

        val moveApplier =
            CheckersMoveApplier(
                moveValidator = moveValidator,
                postMoveEffects = postMoveEffects,
                winConditions = winConditions,
            )

        val game =
            Game(
                board = board,
                turnManager = turnManager,
            )

        return Pair(game, moveApplier)
    }
}
