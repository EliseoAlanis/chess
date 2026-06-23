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
import edu.austral.dissis.chess.network.ChessServer
import edu.austral.dissis.chess.network.ChessClient
import edu.austral.dissis.chess.network.NetworkAdapter

object NetworkConfig {
    // CAMBIAR A FALSE para arrancar solo como cliente remoto
    const val IS_SERVER = true
    const val SERVER_IP = "localhost"
    const val SERVER_PORT = 8080
}

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
        
        if (NetworkConfig.IS_SERVER) {
            val (game, applier) = setupGameAndApplier()
            val engine = edu.austral.dissis.common.game.GameEngine(applier, game)
            val server = ChessServer(engine)
            server.start()
        }

        val client = ChessClient(NetworkConfig.SERVER_IP, NetworkConfig.SERVER_PORT)
        val adapter = setupNetworkAdapter(client)
        val view = setupUI(adapter)

        primaryStage.scene = Scene(view.pane())
        primaryStage.show()

        client.connect()
    }

    private fun setupGameAndApplier(): Pair<Game, edu.austral.dissis.common.movement.applier.MoveApplier> {
        val factory: edu.austral.dissis.common.game.GameFactory = edu.austral.dissis.chess.game.ProgressiveChessFactory()
        return factory.createGame()
    }

    private fun setupNetworkAdapter(client: ChessClient): NetworkAdapter {
        return NetworkAdapter(client) { pieceType ->
            when (pieceType) {
                "chancellor" -> "queen"
                "archbishop" -> "knight"
                else -> pieceType
            }
        }
    }

    private fun setupUI(adapter: NetworkAdapter): GameView {
        val view = GameViewFactory.create(imageResolver)
        
        adapter.process(view.inputEvents())
            .subscribe { gameState ->
                view.applyState(gameState)
            }
            
        return view
    }
}
