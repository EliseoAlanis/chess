package edu.austral.dissis.checkers.ui

import edu.austral.dissis.chess.gui.CachedImageResolver
import edu.austral.dissis.chess.gui.DefaultImageResolver
import edu.austral.dissis.chess.gui.GameView
import edu.austral.dissis.chess.gui.GameViewFactory
import edu.austral.dissis.common.network.GameClient
import edu.austral.dissis.common.network.GameServer
import edu.austral.dissis.common.network.NetworkAdapter
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.GameEngine
import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage

object NetworkConfig {
    const val IS_SERVER = false
    const val SERVER_IP = "localhost"
    const val SERVER_PORT = 8080
}

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

        if (NetworkConfig.IS_SERVER) {
            val engine = setupGameEngine()
            val server = GameServer(engine)
            server.start()
        }

        val client = GameClient(NetworkConfig.SERVER_IP, NetworkConfig.SERVER_PORT)
        val adapter = setupNetworkAdapter(client)
        val view = setupUI(adapter)

        primaryStage.scene = Scene(view.pane())
        primaryStage.show()

        client.connect()
    }

    private fun setupGameEngine(): GameEngine {
        val factory =
            edu.austral.dissis.checkers.game
                .CheckersGameFactory()
        val (game, applier) = factory.createGame()
        return GameEngine(applier, game)
    }

    private fun setupNetworkAdapter(client: GameClient): NetworkAdapter =
        NetworkAdapter(client) { pieceType ->
            when (pieceType) {
                "man" -> "pawn"
                "king" -> "queen"
                else -> pieceType
            }
        }

    private fun setupUI(adapter: NetworkAdapter): GameView {
        val view = GameViewFactory.create(imageResolver)

        adapter
            .process(view.inputEvents())
            .subscribe { gameState ->
                view.applyState(gameState)
            }

        return view
    }
}
