package edu.austral.dissis.common.rules

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.MoveRecord
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color

data class MoveContext(
    val board: Board,
    val path: Path,
    val turn: Color,
    val history: List<MoveRecord> = emptyList()
)
