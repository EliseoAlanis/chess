package edu.austral.dissis.common.rules

import edu.austral.dissis.common.board.Board
import edu.austral.dissis.common.movement.Path
import edu.austral.dissis.common.pieces.Color

import edu.austral.dissis.common.rules.Rule
import edu.austral.dissis.common.rules.MoveContext
import edu.austral.dissis.common.rules.RuleResult

interface BasicRule : Rule {
    // evaluate is inherited from Rule
}
