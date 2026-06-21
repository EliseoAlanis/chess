package edu.austral.dissis.chess.board

import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Piece
import edu.austral.dissis.chess.pieces.PieceFactory

private const val BLACK_BACK_RANK = 8
private const val BLACK_PAWN_RANK = 7
private const val WHITE_PAWN_RANK = 2
private const val WHITE_BACK_RANK = 1

@Suppress("MagicNumber")
class ClassicBoardInitializer : BoardInitializer {
    override fun generateInitialPositions(): Map<Position, Piece> = getWhitePieces() + getBlackPieces()

    private fun getWhitePieces(): Map<Position, Piece> {
        val pieces = mutableMapOf<Position, Piece>()

        // Piezas Mayores
        pieces[Position(WHITE_BACK_RANK, 1)] = PieceFactory.createRook("r_w_1", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, 2)] = PieceFactory.createKnight("n_w_1", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, 3)] = PieceFactory.createBishop("b_w_1", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, 4)] = PieceFactory.createQueen("q_w", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, 5)] = PieceFactory.createKing("k_w", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, 6)] = PieceFactory.createBishop("b_w_2", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, 7)] = PieceFactory.createKnight("n_w_2", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, 8)] = PieceFactory.createRook("r_w_2", Color.BLANCO)

        // Peones Blancos
        for (col in 1..8) {
            pieces[Position(WHITE_PAWN_RANK, col)] = PieceFactory.createPawn("p_w_$col", Color.BLANCO)
        }

        return pieces
    }

    private fun getBlackPieces(): Map<Position, Piece> {
        val pieces = mutableMapOf<Position, Piece>()

        // Piezas Mayores
        pieces[Position(BLACK_BACK_RANK, 1)] = PieceFactory.createRook("r_b_1", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, 2)] = PieceFactory.createKnight("n_b_1", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, 3)] = PieceFactory.createBishop("b_b_1", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, 4)] = PieceFactory.createQueen("q_b", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, 5)] = PieceFactory.createKing("k_b", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, 6)] = PieceFactory.createBishop("b_b_2", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, 7)] = PieceFactory.createKnight("n_b_2", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, 8)] = PieceFactory.createRook("r_b_2", Color.NEGRO)

        // Peones Negros
        for (col in 1..8) {
            pieces[Position(BLACK_PAWN_RANK, col)] = PieceFactory.createPawn("p_b_$col", Color.NEGRO)
        }

        return pieces
    }
}
