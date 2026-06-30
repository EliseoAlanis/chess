package edu.austral.dissis.common.network

data class MovePayload(
    var fromRow: Int = 0,
    var fromCol: Int = 0,
    var toRow: Int = 0,
    var toCol: Int = 0,
)

data class PiecePayload(
    var id: String = "",
    var color: String = "",
    var row: Int = 0,
    var col: Int = 0,
    var pieceId: String = "",
)

data class GameStatePayload(
    var pieces: List<PiecePayload> = emptyList(),
    var currentPlayer: String = "",
    var isGameOver: Boolean = false,
    var winnerColor: String? = null,
    var boardCols: Int = 8,
    var boardRows: Int = 8,
    var canUndo: Boolean = false,
    var canRedo: Boolean = false,
)

class UndoPayload
class RedoPayload

data class InitialConnectionPayload(
    var assignedColor: String = "SPECTATOR", // "WHITE", "BLACK", "SPECTATOR"
    var gameState: GameStatePayload = GameStatePayload(),
)
