package edu.austral.dissis.common.rules

interface Rule {
    fun evaluate(context: MoveContext): RuleResult
}
