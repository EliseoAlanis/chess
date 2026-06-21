package edu.austral.dissis.chess.rules.special

import edu.austral.dissis.chess.movement.effect.PostMoveEffect

sealed interface RuleResult {
    data class Valid(val effects: List<PostMoveEffect> = emptyList()) : RuleResult
    data object Invalid : RuleResult
}
