package edu.austral.dissis.chess.ui

import edu.austral.dissis.chess.adapter.ChessEngineAdapter
import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.board.ClassicBoardInitializer
import edu.austral.dissis.chess.game.Game
import edu.austral.dissis.chess.movement.validator.MoveValidator
import edu.austral.dissis.chess.rules.special.PawnInitialDoubleMovementRule
import edu.austral.dissis.chess.rules.special.EnPassantRule
import edu.austral.dissis.chess.turn.StandardTurnManager
import edu.austral.dissis.chess.gui.GameViewFactory
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.rules.special.CanCastleRule
import edu.austral.dissis.chess.movement.effect.PromotionEffect
import edu.austral.dissis.chess.movement.effect.EnPassantCaptureEffect
import edu.austral.dissis.chess.movement.effect.CastlingEffect
import edu.austral.dissis.chess.game.condition.CheckmateCondition
import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.movement.applier.MoveApplier
import edu.austral.dissis.chess.movement.validator.AttackDetector
import edu.austral.dissis.chess.rules.basic.BoardLimitsRule
import edu.austral.dissis.chess.rules.basic.ClearPathRule
import edu.austral.dissis.chess.rules.basic.CorrectTurnRule
import edu.austral.dissis.chess.rules.basic.FriendlyFireRule
import edu.austral.dissis.chess.rules.basic.NoSelfCheckRule
import javafx.application.Application
import javafx.application.Application.launch
import javafx.scene.Scene
import javafx.stage.Stage

// Alias para evitar colisión de nombres
import edu.austral.dissis.chess.board.BoardSize as DomainBoardSize

fun main() {
    launch(ChessGameApplication::class.java)
}

class ChessGameApplication : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    companion object {
        const val GAME_TITLE = "Chess"
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = GAME_TITLE
        val (domainGame, _) = edu.austral.dissis.chess.game.ChessGameFactory.createDefaultGameAndApplier()

        // 3. Envolvemos el juego en el Adapter
        val adapter = ChessEngineAdapter(domainGame)

        // 4. Creamos la vista usando la fábrica
        val view = GameViewFactory.create(imageResolver)

        // 5. Conectamos los flujos reactivos manualmente
        adapter.process(view.inputEvents())
            .subscribe { gameState ->
                view.applyState(gameState)
            }

        // 6. Mostramos la ventana
        primaryStage.scene = Scene(view.pane())
        primaryStage.show()
    }
}
