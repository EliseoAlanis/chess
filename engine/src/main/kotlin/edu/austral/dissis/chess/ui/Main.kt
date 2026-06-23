package edu.austral.dissis.chess.ui

import edu.austral.dissis.chess.adapter.ChessEngineAdapter
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
        val adapter = setupAdapter(game, applier)
        val view = setupUI(adapter)

        primaryStage.scene = Scene(view.pane())
        primaryStage.show()
    }

    private fun setupGameAndApplier(): Pair<Game, edu.austral.dissis.common.movement.applier.MoveApplier> {
        return edu.austral.dissis.chess.game.ChessGameFactory.createDefaultGameAndApplier()
    }

    private fun setupAdapter(game: Game, applier: edu.austral.dissis.common.movement.applier.MoveApplier): ChessEngineAdapter {
        return ChessEngineAdapter(game, applier)
    }

    private fun setupUI(adapter: ChessEngineAdapter): GameView {
        val view = GameViewFactory.create(imageResolver)
        
        adapter.process(view.inputEvents())
            .subscribe { gameState ->
                view.applyState(gameState)
            }
            
        return view
    }
}
