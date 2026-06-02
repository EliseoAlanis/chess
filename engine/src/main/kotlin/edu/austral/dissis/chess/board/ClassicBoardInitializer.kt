package edu.austral.dissis.chess.board

import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Piece
import edu.austral.dissis.chess.pieces.PieceFactory

private const val WHITE_BACK_RANK = 0
private const val BLACK_BACK_RANK = 7
private const val QUEEN_COLUMN = 3

class ClassicBoardInitializer : BoardInitializer {
    override fun generateInitialPositions(): Map<Position, Piece> = getWhitePieces() + getBlackPieces()

    private fun getWhitePieces(): Map<Position, Piece> = getWhiteBackRank()

    private fun getBlackPieces(): Map<Position, Piece> = getBlackBackRank()

    private fun getWhiteBackRank(): Map<Position, Piece> =
        mapOf(
            Position(WHITE_BACK_RANK, 0) to PieceFactory.createRook("r_w_1", Color.BLANCO),
            Position(WHITE_BACK_RANK, 1) to PieceFactory.createKnight("n_w_1", Color.BLANCO),
            Position(WHITE_BACK_RANK, 2) to PieceFactory.createBishop("b_w_1", Color.BLANCO),
            Position(WHITE_BACK_RANK, QUEEN_COLUMN) to PieceFactory.createQueen("q_w", Color.BLANCO),
        )

    private fun getBlackBackRank(): Map<Position, Piece> =
        mapOf(
            Position(BLACK_BACK_RANK, 0) to PieceFactory.createRook("r_b_1", Color.NEGRO),
            Position(BLACK_BACK_RANK, 1) to PieceFactory.createKnight("n_b_1", Color.NEGRO),
            Position(BLACK_BACK_RANK, 2) to PieceFactory.createBishop("b_b_1", Color.NEGRO),
            Position(BLACK_BACK_RANK, QUEEN_COLUMN) to PieceFactory.createQueen("q_b", Color.NEGRO),
        )
}
