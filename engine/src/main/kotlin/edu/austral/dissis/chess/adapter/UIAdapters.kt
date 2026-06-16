package edu.austral.dissis.chess.adapter

import edu.austral.dissis.chess.board.Board
import edu.austral.dissis.chess.pieces.Color
import edu.austral.dissis.chess.pieces.Piece
// Alias para evitar colisión de nombres
import edu.austral.dissis.chess.movement.Position as DomainPosition

import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.chess.gui.Position as UIPosition

fun Color.toUIColor(): PlayerColor = when (this) {
    Color.BLANCO -> PlayerColor.WHITE
    Color.NEGRO -> PlayerColor.BLACK
}

fun DomainPosition.toUIPosition(): UIPosition = UIPosition(this.row + 1, this.col + 1)

fun UIPosition.toDomainPosition(): DomainPosition = DomainPosition(this.row - 1, this.column - 1)
fun Board.toUIPieces(): List<ChessPiece> {
    return this.positions.map { (domainPos, piece) ->
        val pieceName = piece.pieceType.name.lowercase()
        // La clase ChessPiece de la UI guarda la posición internamente
        ChessPiece(
            id = piece.id,
            color = piece.color.toUIColor(),
            position = domainPos.toUIPosition(),
            pieceId = pieceName
        )
    }
}
