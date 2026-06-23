package edu.austral.dissis.common.rules

import edu.austral.dissis.common.movement.effect.PostMoveEffect

sealed interface RuleResult {
    data class Valid(
        val effects: List<PostMoveEffect> = emptyList(),
    ) : RuleResult

    data object Invalid : RuleResult
}
