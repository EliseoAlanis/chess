package edu.austral.dissis.chess.ui

import edu.austral.dissis.common.adapter.EngineAdapter
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.chess.gui.GameViewFactory
import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

fun main() {
    Application.launch(ChessGameApplication::class.java)
}

class ChessGameApplication : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    
    companion object {
        const val GAME_TITLE = "Chess"
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = GAME_TITLE
        
        val (game, applier) = setupGameAndApplier()
        val engine = edu.austral.dissis.common.game.GameEngine(applier, game)
        val adapter = setupAdapter(engine)
        val view = setupUI(adapter)

        primaryStage.scene = Scene(view.pane())
        primaryStage.show()
    }

    private fun setupGameAndApplier(): Pair<Game, edu.austral.dissis.common.movement.applier.MoveApplier> {
        val factory: edu.austral.dissis.common.game.GameFactory = edu.austral.dissis.chess.game.ProgressiveChessFactory()
        return factory.createGame()
    }

    private fun setupAdapter(engine: edu.austral.dissis.common.game.GameEngine): EngineAdapter {
        return EngineAdapter(engine) { pieceType ->
            when (pieceType) {
                "chancellor" -> "queen"
                "archbishop" -> "knight"
                else -> pieceType
            }
        }
    }

    private fun setupUI(adapter: EngineAdapter): GameView {
        val view = GameViewFactory.create(imageResolver)
        
        adapter.process(view.inputEvents())
            .subscribe { gameState ->
                view.applyState(gameState)
            }
            
        return view
    }
}
