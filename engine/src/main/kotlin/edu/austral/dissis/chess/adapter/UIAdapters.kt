package edu.austral.dissis.chess.adapter

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.pieces.Color
import edu.austral.dissis.common.movement.Position as DomainPosition

import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.chess.gui.Position as UIPosition

fun Color.toUIColor(): PlayerColor = when (this) {
    Color.WHITE -> PlayerColor.WHITE
    Color.BLACK -> PlayerColor.BLACK
}

fun DomainPosition.toUIPosition(): UIPosition = UIPosition(this.row, this.col)

fun UIPosition.toDomainPosition(): DomainPosition = DomainPosition(this.row, this.column)
fun Board.toUIPieces(): List<ChessPiece> {
    return this.positions.map { (domainPos, piece) ->
        val pieceName = piece.pieceType.lowercase()
        // La clase ChessPiece de la UI guarda la posiciÃ³n internamente
        ChessPiece(
            id = piece.id,
            color = piece.color.toUIColor(),
            position = domainPos.toUIPosition(),
            pieceId = pieceName
        )
    }
}
