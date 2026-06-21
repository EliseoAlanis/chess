package edu.austral.dissis.chess.board

import edu.austral.dissis.chess.movement.Position
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Piece
import edu.austral.dissis.chess.pieces.PieceFactory

private const val BLACK_BACK_RANK = 8
private const val BLACK_PAWN_RANK = 7
private const val WHITE_PAWN_RANK = 2
private const val WHITE_BACK_RANK = 1

class ClassicBoardInitializer : BoardInitializer {
    override fun generateInitialPositions(): Map<Position, Piece> = getWhitePieces() + getBlackPieces()

    val one = 1
    val two = 2
    val three = 3
    val four = 4
    val five = 5
    val six = 6
    val seven = 7
    val eight = 8

    private fun getWhitePieces(): Map<Position, Piece> {
        val pieces = mutableMapOf<Position, Piece>()

        // Piezas Mayores
        pieces[Position(WHITE_BACK_RANK, one)] = PieceFactory.createRook("r_w_1", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, two)] = PieceFactory.createKnight("n_w_1", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, three)] = PieceFactory.createBishop("b_w_1", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, four)] = PieceFactory.createQueen("q_w", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, five)] = PieceFactory.createKing("k_w", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, six)] = PieceFactory.createBishop("b_w_2", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, seven)] = PieceFactory.createKnight("n_w_2", Color.BLANCO)
        pieces[Position(WHITE_BACK_RANK, eight)] = PieceFactory.createRook("r_w_2", Color.BLANCO)

        // Peones Blancos
        for (col in one..eight) {
            pieces[Position(WHITE_PAWN_RANK, col)] = PieceFactory.createPawn("p_w_$col", Color.BLANCO)
        }

        return pieces
    }

    private fun getBlackPieces(): Map<Position, Piece> {
        val pieces = mutableMapOf<Position, Piece>()

        // Piezas Mayores
        pieces[Position(BLACK_BACK_RANK, one)] = PieceFactory.createRook("r_b_1", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, two)] = PieceFactory.createKnight("n_b_1", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, three)] = PieceFactory.createBishop("b_b_1", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, four)] = PieceFactory.createQueen("q_b", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, five)] = PieceFactory.createKing("k_b", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, six)] = PieceFactory.createBishop("b_b_2", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, seven)] = PieceFactory.createKnight("n_b_2", Color.NEGRO)
        pieces[Position(BLACK_BACK_RANK, eight)] = PieceFactory.createRook("r_b_2", Color.NEGRO)

        // Peones Negros
        for (col in one..eight) {
            pieces[Position(BLACK_PAWN_RANK, col)] = PieceFactory.createPawn("p_b_$col", Color.NEGRO)
        }

        return pieces
    }
}
