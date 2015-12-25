package com.trulinc.traqr.util

/**
 * AttackingBotIncrease = Status for when friendly bot counts go up during an attack on an enemy zone
 * AttackingBotDecrease = Status for when friendly bot counts go down up during an attack on an enemy zone
 * UnderAttackPlasma = Status for when friendly bot counts go down up while controlling a zone (Plasma)
 * UnderAttack = Status for when friendly bot counts go down up while controlling a zone and enemy bots go up
 * UnderAttack = Status for when friendly bot counts go down up while controlling a zone and enemy bots go up
 * Stacking = Status for when friendly bot counts go up up while controlling a zone and no enemy bots
 * StackingNeedsClear = Status for when friendly bot counts go up up while controlling a zone and no enemy bots are present
 * EnemyStacking = Status for when enemy bot counts go up up in a zone they control
 *
 */
public enum ZoneChangeType {

    AttackingBotIncrease(NotificationStrategy.Ignore),
    AttackingBotDecrease(NotificationStrategy.Ignore),
    AttackingNoChange(NotificationStrategy.Ignore),
    UnderAttackPlasma(NotificationStrategy.Immediate),
    UnderAttack(NotificationStrategy.Immediate),
    Stacking(NotificationStrategy.Ignore),
    StackingNeedsClear(NotificationStrategy.Ignore),
    EnemyStacking(NotificationStrategy.Ignore),
    Clear(NotificationStrategy.Ignore),
    NeedsClear(NotificationStrategy.Immediate),
    ZoneLost(NotificationStrategy.Immediate),
    ZoneGained(NotificationStrategy.Immediate),
    NoChange(NotificationStrategy.Immediate)

    NotificationStrategy notificationStrategy

    ZoneChangeType(NotificationStrategy notificationStrategy) {
        this.notificationStrategy = notificationStrategy
    }
}