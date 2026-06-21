package edu.austral.dissis.chess.exam

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.game.Game
import edu.austral.dissis.chess.game.MoveResult
import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Piece
import edu.austral.dissis.chess.pieces.PieceFactory
import edu.austral.dissis.chess.pieces.PieceType
import edu.austral.dissis.chess.test.TestBoard
import edu.austral.dissis.chess.test.TestPiece
import edu.austral.dissis.chess.test.TestPosition
import edu.austral.dissis.chess.test.TestSize
import edu.austral.dissis.chess.test.game.TestGameRunner
import edu.austral.dissis.chess.test.game.TestMoveFailure
import edu.austral.dissis.chess.test.game.TestMoveResult
import edu.austral.dissis.chess.test.game.TestMoveSuccess
import edu.austral.dissis.chess.board.BoardSize
import edu.austral.dissis.chess.turn.StandardTurnManager
import edu.austral.dissis.chess.movement.applier.MoveApplier


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
            val (defaultGame, defaultApplier) = createDefaultGameAndApplier()
            this.currentGame = game ?: defaultGame
            this.currentApplier = moveApplier ?: defaultApplier
        } else {
            this.currentGame = game
            this.currentApplier = moveApplier
        }
    }

    private fun createDefaultGameAndApplier(): Pair<Game, MoveApplier> {
        return edu.austral.dissis.chess.game.ChessGameFactory.createDefaultGameAndApplier()
    }

    override fun executeMove(
        from: TestPosition,
        to: TestPosition,
    ): TestMoveResult {
        val rows = currentGame.board.size.rows
        val origin = Position(from.row, from.col)
        val dest = Position(to.row, to.col)
        
        return when (val result = currentApplier.executeMove(currentGame, origin, dest)) {
            is MoveResult.Success -> {
                val newPastGames = pastGames + currentGame
                val newRunner = DummyTestGameRunner(result.newGameState, currentApplier, newPastGames, emptyList())
                when (result.newGameState.status) {
                    edu.austral.dissis.chess.game.GameStatus.WHITE_WINS ->
                        edu.austral.dissis.chess.test.game.WhiteCheckMate(newRunner.getBoard())
                    edu.austral.dissis.chess.game.GameStatus.BLACK_WINS ->
                        edu.austral.dissis.chess.test.game.BlackCheckMate(newRunner.getBoard())
                    edu.austral.dissis.chess.game.GameStatus.DRAW ->
                        edu.austral.dissis.chess.test.game.TestMoveDraw(newRunner.getBoard())
                    else -> TestMoveSuccess(newRunner)
                }
            }
            is MoveResult.Failure -> {
                TestMoveFailure(getBoard())
            }
        }
    }

    override fun getBoard(): TestBoard {
        val rows = currentGame.board.size.rows
        val testPieces = currentGame.board.positions.map { (pos, piece) ->
            TestPosition(pos.row, pos.col) to toTestPiece(piece)
        }.toMap()
        
        return TestBoard(TestSize(currentGame.board.size.columns, rows), testPieces)
    }

    override fun redo(): TestMoveResult {
        if (futureGames.isEmpty()) return TestMoveFailure(getBoard())
        val gameToRedo = futureGames.last()
        val newFutureGames = futureGames.dropLast(1)
        val newPastGames = pastGames + currentGame
        val newRunner = DummyTestGameRunner(gameToRedo, currentApplier, newPastGames, newFutureGames)
        return TestMoveSuccess(newRunner)
    }

    override fun undo(): TestMoveResult {
        if (pastGames.isEmpty()) return TestMoveFailure(getBoard())
        val gameToUndo = pastGames.last()
        val newPastGames = pastGames.dropLast(1)
        val newFutureGames = futureGames + currentGame
        val newRunner = DummyTestGameRunner(gameToUndo, currentApplier, newPastGames, newFutureGames)
        return TestMoveSuccess(newRunner)
    }

    override fun withBoard(board: TestBoard): TestGameRunner {
        val (cols, rows) = board.size
        val domainPositions = board.pieces.map { (testPos, testPiece) ->
            Position(testPos.row, testPos.col) to toDomainPiece(testPiece)
        }.toMap()
        
        val newBoard = Board(domainPositions, BoardSize(cols, rows))
        
        val newGame = currentGame.copy(
            board = newBoard, 
            turnManager = StandardTurnManager(currentTurn = Color.BLANCO),
            moveHistory = emptyList()
        )
        return DummyTestGameRunner(newGame, currentApplier, emptyList(), emptyList())
    }
    
    private fun toTestPiece(piece: Piece): TestPiece {
        val colorSymbol = if (piece.color == Color.BLANCO) 'W' else 'B'
        val typeSymbol = when (piece.pieceType) {
            PieceType.PAWN -> 'P'
            PieceType.ROOK -> 'R'
            PieceType.KNIGHT -> 'N'
            PieceType.BISHOP -> 'B'
            PieceType.QUEEN -> 'Q'
            PieceType.KING -> 'K'
            else -> '?'
        }
        return edu.austral.dissis.chess.test.TestPiece(typeSymbol, colorSymbol)
    }
    
    private fun toDomainPiece(testPiece: TestPiece): Piece {
        val color = if (testPiece.playerColorSymbol.uppercaseChar() == 'W') Color.BLANCO else Color.NEGRO
        val typeChar = testPiece.pieceTypeSymbol.uppercaseChar()
        val typeName = when (typeChar) {
            'K' -> "king"
            'R' -> "rook"
            'P' -> "pawn"
            'N' -> "knight"
            'B' -> "bishop"
            'Q' -> "queen"
            else -> "unknown"
        }
        val id = "${typeName}_" + java.util.UUID.randomUUID().toString() 
        return when (typeChar) {
            'P' -> PieceFactory.createPawn(id, color)
            'R' -> PieceFactory.createRook(id, color)
            'N' -> PieceFactory.createKnight(id, color)
            'B' -> PieceFactory.createBishop(id, color)
            'Q' -> PieceFactory.createQueen(id, color)
            'K' -> PieceFactory.createKing(id, color)
            else -> PieceFactory.createPawn(id, color)
        }
    }
}
