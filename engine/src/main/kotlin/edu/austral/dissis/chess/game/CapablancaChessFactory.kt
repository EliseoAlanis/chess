package edu.austral.dissis.chess.game

import edu.austral.dissis.chess.board.CapablancaBoardFactory
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.chess.game.condition.CheckmateCondition
import edu.austral.dissis.common.movement.applier.MoveApplier
import edu.austral.dissis.chess.movement.applier.DefaultMoveApplier
import edu.austral.dissis.chess.movement.effect.PromotionEffect
import edu.austral.dissis.chess.movement.validator.AttackDetector
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.chess.rules.basic.*
import edu.austral.dissis.chess.rules.special.CanCastleRule
import edu.austral.dissis.chess.rules.special.EnPassantRule
import edu.austral.dissis.chess.rules.special.PawnInitialDoubleMovementRule
import edu.austral.dissis.chess.turn.StandardTurnManager

import edu.austral.dissis.common.game.GameFactory
import edu.austral.dissis.common.board.BoardFactory
import edu.austral.dissis.chess.board.ClassicBoardFactory

class CapablancaChessFactory(
    private val boardFactory: BoardFactory = CapablancaBoardFactory()
) : GameFactory {
    override fun createGame(): Pair<Game, MoveApplier> {
        val initialBoard = boardFactory.createBoard()
        val attackDetector = AttackDetector()

        val basicRules = listOf(
            BoardLimitsRule(),
            CorrectTurnRule(),
            ClearPathRule(),
            FriendlyFireRule(),
            NoSelfCheckRule(attackDetector)
        )
        val specialRules = listOf(
            PawnInitialDoubleMovementRule(),
            CanCastleRule(),
            EnPassantRule()
        )
        val validator = edu.austral.dissis.chess.movement.validator.DefaultMoveValidator(basicRules, specialRules)

        val globalPostMoveEffects = listOf(PromotionEffect())
        val winConditions = listOf(CheckmateCondition(validator, attackDetector))
        val moveApplier = DefaultMoveApplier(validator, globalPostMoveEffects, winConditions)

        val game = Game(
            board = initialBoard,
            turnManager = StandardTurnManager(currentTurn = Color.WHITE),
        )

        return Pair(game, moveApplier)
    }
}
