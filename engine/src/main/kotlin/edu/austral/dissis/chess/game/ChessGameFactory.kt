package edu.austral.dissis.chess.game

import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.chess.board.ClassicBoardInitializer
import edu.austral.dissis.chess.game.condition.CheckmateCondition
import edu.austral.dissis.common.movement.applier.MoveApplier
import edu.austral.dissis.chess.movement.applier.DefaultMoveApplier
import edu.austral.dissis.chess.movement.effect.PromotionEffect
import edu.austral.dissis.common.movement.validator.AttackDetector
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.chess.rules.basic.*
import edu.austral.dissis.chess.rules.special.CanCastleRule
import edu.austral.dissis.chess.rules.special.EnPassantRule
import edu.austral.dissis.chess.rules.special.PawnInitialDoubleMovementRule
import edu.austral.dissis.chess.turn.StandardTurnManager

object ChessGameFactory {
    fun createDefaultGameAndApplier(): Pair<Game, MoveApplier> {
        val size = BoardSize(8, 8)
        val initialBoard = Board(ClassicBoardInitializer().generateInitialPositions(), size)
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
            rules = basicRules,
            turnManager = StandardTurnManager(currentTurn = Color.WHITE),
            moveValidator = validator,
            postMoveEffects = globalPostMoveEffects,
            winConditions = winConditions
        )

        return Pair(game, moveApplier)
    }
}
