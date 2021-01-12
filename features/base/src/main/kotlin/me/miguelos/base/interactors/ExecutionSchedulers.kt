package me.miguelos.base.interactors

import io.reactivex.rxjava3.core.Scheduler

interface ExecutionSchedulers {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun ui(): Scheduler
}
