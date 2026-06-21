package edu.austral.dissis.chess.game

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.board.BoardSize
import edu.austral.dissis.chess.board.ClassicBoardInitializer
import edu.austral.dissis.chess.game.condition.CheckmateCondition
import edu.austral.dissis.chess.movement.applier.MoveApplier
import edu.austral.dissis.chess.movement.effect.PromotionEffect
import edu.austral.dissis.chess.movement.validator.AttackDetector
import edu.austral.dissis.chess.movement.validator.MoveValidator
import edu.austral.dissis.chess.pieces.Color
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
        val validator = MoveValidator(basicRules, specialRules)

        val globalPostMoveEffects = listOf(PromotionEffect())
        val winConditions = listOf(CheckmateCondition(validator, attackDetector))
        val moveApplier = MoveApplier(validator, globalPostMoveEffects, winConditions)

        val game = Game(
            board = initialBoard,
            turnManager = StandardTurnManager(currentTurn = Color.BLANCO),
            moveApplier = moveApplier
        )

        return Pair(game, moveApplier)
    }
}
