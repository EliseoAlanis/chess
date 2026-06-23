package edu.austral.dissis.chess.board

import edu.austral.dissis.chess.pieces.PieceFactory

import edu.austral.dissis.common.board.BoardInitializer

import edu.austral.dissis.common.movement.Position
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.pieces.Piece

private const val BLACK_BACK_RANK = 8
private const val BLACK_PAWN_RANK = 7
private const val WHITE_PAWN_RANK = 2
private const val backRank = 1

@Suppress("MagicNumber")
class CapablancaBoardInitializer : BoardInitializer {
    override fun generateInitialPositions(): Map<Position, Piece> =
        getPiecesForColor(Color.WHITE, backRank, WHITE_PAWN_RANK) +
                getPiecesForColor(Color.BLACK, BLACK_BACK_RANK, BLACK_PAWN_RANK)

    private fun getPiecesForColor(color: Color, rank: Int, pawnRank: Int): Map<Position, Piece> {
        val pieces = mutableMapOf<Position, Piece>()
        val suffix = if (color == Color.WHITE) "w" else "b"

        // Piezas Mayores
        pieces[Position(rank, 1)] = PieceFactory.createRook("r_${suffix}_1", color)
        pieces[Position(rank, 2)] = PieceFactory.createKnight("n_${suffix}_1", color)
        pieces[Position(rank, 3)] = PieceFactory.createBishop("b_${suffix}_1", color)
        pieces[Position(rank, 4)] = PieceFactory.createQueen("q_$suffix", color)
        pieces[Position(rank, 5)] = PieceFactory.createKing("k_$suffix", color)
        pieces[Position(rank, 6)] = PieceFactory.createBishop("b_${suffix}_2", color)
        pieces[Position(rank, 7)] = PieceFactory.createKnight("n_${suffix}_2", color)
        pieces[Position(rank, 8)] = PieceFactory.createRook("r_${suffix}_2", color)
        pieces[Position(rank, 9)] = PieceFactory.createChancellor("ch_${suffix}_1", color)
        pieces[Position(rank, 10)] = PieceFactory.createArchbishop("ab_${suffix}_1", color)


        // Peones
        for (col in 1..10) {
            pieces[Position(pawnRank, col)] = PieceFactory.createPawn("p_${suffix}_$col", color)
        }

        return pieces
    }
}
