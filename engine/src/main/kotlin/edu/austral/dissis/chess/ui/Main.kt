package edu.austral.dissis.chess.ui

import edu.austral.dissis.chess.adapter.ChessEngineAdapter
import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.board.ClassicBoardInitializer
import edu.austral.dissis.chess.game.Game
import edu.austral.dissis.chess.movement.validator.MoveValidator
import edu.austral.dissis.chess.rules.basic.*
import edu.austral.dissis.chess.rules.special.PawnInitialDoubleMoveRule
import edu.austral.dissis.chess.turn.StandardTurnManager
import edu.austral.dissis.chess.gui.*
import edu.austral.dissis.chess.rules.special.CanCastleRule
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

        // 1. Configuramos las reglas del dominio
        val basicRules = listOf(BoardLimitsRule(), CorrectTurnRule(), ClearPathRule(), FriendlyFireRule(),
            NoSelfCheckRule())
        val specialRules = listOf(PawnInitialDoubleMoveRule(), CanCastleRule())
        val validator = MoveValidator(basicRules, specialRules)

        // 2. Inicializamos el estado del juego
        val size = DomainBoardSize(8, 8)
        val initialBoard = Board(ClassicBoardInitializer().generateInitialPositions(), size)
        val domainGame = Game(initialBoard, basicRules, StandardTurnManager(), emptyList(), validator)

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
