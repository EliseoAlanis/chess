package edu.austral.dissis.checkers.ui

import edu.austral.dissis.checkers.adapter.CheckersEngineAdapter
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.GameEngine
import edu.austral.dissis.chess.gui.GameViewFactory
import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

fun main() {
    Application.launch(CheckersGameApplication::class.java)
}

class CheckersGameApplication : Application() {
    private val imageResolver = CachedImageResolver(DefaultImageResolver())
    
    companion object {
        const val GAME_TITLE = "Checkers"
    }

    override fun start(primaryStage: Stage) {
        primaryStage.title = GAME_TITLE
        
        val engine = setupGameEngine()
        val adapter = setupAdapter(engine)
        val view = setupUI(adapter)

        primaryStage.scene = Scene(view.pane())
        primaryStage.show()
    }

    private fun setupGameEngine(): GameEngine {
        val factory = edu.austral.dissis.checkers.game.CheckersGameFactory()
        val (game, applier) = factory.createGame()
        return GameEngine(applier, game)
    }

    private fun setupAdapter(engine: GameEngine): CheckersEngineAdapter {
        return CheckersEngineAdapter(engine)
    }

    private fun setupUI(adapter: CheckersEngineAdapter): GameView {
        val view = GameViewFactory.create(imageResolver)
        
        adapter.process(view.inputEvents())
            .subscribe { gameState ->
                view.applyState(gameState)
            }
            
        return view
    }
}
