package edu.austral.dissis.chess.exam

import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.game.Game
import edu.austral.dissis.common.game.MoveResult
import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece
import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.chess.test.TestBoard
import edu.austral.dissis.chess.test.TestPiece
import edu.austral.dissis.chess.test.TestPosition
import edu.austral.dissis.chess.test.TestSize
import edu.austral.dissis.chess.test.game.TestGameRunner
import edu.austral.dissis.chess.test.game.TestMoveFailure
import edu.austral.dissis.chess.test.game.TestMoveResult
import edu.austral.dissis.chess.test.game.TestMoveSuccess
import edu.austral.dissis.common.board.BoardSize
import edu.austral.dissis.chess.turn.StandardTurnManager
import edu.austral.dissis.common.movement.applier.MoveApplier
import java.util.UUID

class DummyTestGameRunner(
    game: Game? = null,
    moveApplier: MoveApplier? = null,
    private val pastGames: List<Game> = emptyList(),
    private val futureGames: List<Game> = emptyList()
) : TestGameRunner {

    private val currentApplier: MoveApplier
    private val currentGame: Game

    init {
        if (game == null || moveApplier == null) {
            val (defaultGame, defaultApplier) = edu.austral.dissis.chess.game.ChessGameFactory.createDefaultGameAndApplier()
            this.currentGame = game ?: defaultGame
            this.currentApplier = moveApplier ?: defaultApplier
        } else {
            this.currentGame = game
            this.currentApplier = moveApplier
        }
    }

    override fun executeMove(from: TestPosition, to: TestPosition): TestMoveResult {
        val result = currentApplier.executeMove(currentGame, Position(from.row, from.col), Position(to.row, to.col))
        return if (result is MoveResult.Success) handleMoveSuccess(result) else TestMoveFailure(getBoard())
    }

    private fun handleMoveSuccess(result: MoveResult.Success): TestMoveResult {
        val newRunner = DummyTestGameRunner(result.newGameState, currentApplier, pastGames + currentGame, emptyList())
        return when (result.newGameState.status) {
            edu.austral.dissis.common.game.GameStatus.WHITE_WINS -> edu.austral.dissis.chess.test.game.WhiteCheckMate(newRunner.getBoard())
            edu.austral.dissis.common.game.GameStatus.BLACK_WINS -> edu.austral.dissis.chess.test.game.BlackCheckMate(newRunner.getBoard())
            edu.austral.dissis.common.game.GameStatus.DRAW -> edu.austral.dissis.chess.test.game.TestMoveDraw(newRunner.getBoard())
            else -> TestMoveSuccess(newRunner)
        }
    }

    override fun getBoard(): TestBoard {
        val testPieces = currentGame.board.positions.map { (pos, piece) ->
            TestPosition(pos.row, pos.col) to toTestPiece(piece)
        }.toMap()
        
        return TestBoard(TestSize(currentGame.board.size.columns, currentGame.board.size.rows), testPieces)
    }

    override fun redo(): TestMoveResult {
        if (futureGames.isEmpty()) return TestMoveFailure(getBoard())
        val gameToRedo = futureGames.last()
        val newRunner = DummyTestGameRunner(gameToRedo, currentApplier, pastGames + currentGame, futureGames.dropLast(1))
        return TestMoveSuccess(newRunner)
    }

    override fun undo(): TestMoveResult {
        if (pastGames.isEmpty()) return TestMoveFailure(getBoard())
        val gameToUndo = pastGames.last()
        val newRunner = DummyTestGameRunner(gameToUndo, currentApplier, pastGames.dropLast(1), futureGames + currentGame)
        return TestMoveSuccess(newRunner)
    }

    override fun withBoard(board: TestBoard): TestGameRunner {
        val newGame = currentGame.copy(
            board = buildDomainBoard(board), 
            turnManager = StandardTurnManager(currentTurn = Color.WHITE),
            moveHistory = emptyList()
        )
        return DummyTestGameRunner(newGame, currentApplier, emptyList(), emptyList())
    }

    private fun buildDomainBoard(board: TestBoard): Board {
        val domainPositions = board.pieces.mapKeys { Position(it.key.row, it.key.col) }.mapValues { toDomainPiece(it.value) }
        return Board(domainPositions, BoardSize(board.size.cols, board.size.rows))
    }
    
    private val testPieceSymbols = mapOf(
        PieceType.PAWN to 'P', PieceType.ROOK to 'R', PieceType.KNIGHT to 'N',
        PieceType.BISHOP to 'B', PieceType.QUEEN to 'Q', PieceType.KING to 'K'
    )

    private fun toTestPiece(piece: Piece): TestPiece {
        val colorSymbol = if (piece.color == Color.WHITE) 'W' else 'B'
        val typeSymbol = testPieceSymbols[piece.pieceType] ?: '?'
        return TestPiece(typeSymbol, colorSymbol)
    }
    
    private val pieceCreators = mapOf<Char, (String, Color) -> Piece>(
        'P' to PieceFactory::createPawn, 'R' to PieceFactory::createRook, 'N' to PieceFactory::createKnight,
        'B' to PieceFactory::createBishop, 'Q' to PieceFactory::createQueen, 'K' to PieceFactory::createKing
    )

    private fun toDomainPiece(testPiece: TestPiece): Piece {
        val color = if (testPiece.playerColorSymbol.uppercaseChar() == 'W') Color.WHITE else Color.BLACK
        val typeChar = testPiece.pieceTypeSymbol.uppercaseChar()
        val creator = pieceCreators[typeChar] ?: PieceFactory::createPawn
        return creator("${typeChar}_${UUID.randomUUID()}", color)
    }
}
