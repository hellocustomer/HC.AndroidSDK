package com.hellocustomer.sdk.utility

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

internal class LiveEvent<T> : MediatorLiveData<T>() {
    private val observers = ConcurrentHashMap<LifecycleOwner, MutableSet<ObserverWrapper<in T>>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val wrapper = ObserverWrapper(observer)
        val set = observers[owner]
        set?.apply {
            add(wrapper)
        } ?: run {
            val newSet =
                Collections.newSetFromMap(ConcurrentHashMap<ObserverWrapper<in T>, Boolean>())
            newSet.add(wrapper)
            observers[owner] = newSet
        }
        super.observe(owner, wrapper)
    }

    override fun removeObservers(owner: LifecycleOwner) {
        observers.remove(owner)
        super.removeObservers(owner)
    }

    override fun removeObserver(observer: Observer<in T>) {
        observers.forEach {
            if (it.value.remove<Observer<in T>>(observer)) {
                if (it.value.isEmpty()) {
                    observers.remove(it.key)
                }
                return@forEach
            }
        }
        super.removeObserver(observer)
    }

    @MainThread
    override fun setValue(t: T?) {
        observers.forEach { it.value.forEach { wrapper -> wrapper.newValue() } }
        super.setValue(t)
    }

    @WorkerThread
    override fun postValue(t: T?) {
        observers.forEach { it.value.forEach { wrapper -> wrapper.newValue() } }
        super.postValue(t)
    }

    private class ObserverWrapper<T>(private val observer: Observer<T>) : Observer<T> {
        private val pending = AtomicBoolean(false)
        override fun onChanged(t: T?) {
            if (pending.compareAndSet(true, false)) {
                observer.onChanged(t)
            }
        }

        fun newValue() {
            pending.set(true)
        }
    }
}

/**
 * Used for cases where T is Unit, to make calls cleaner.
 */
@MainThread
internal fun LiveEvent<Unit>.call() {
    value = Unit
}

/**
 * Used for cases where T is Unit, to make calls cleaner.
 */
@WorkerThread
internal fun LiveEvent<Unit>.post() {
    postValue(Unit)
}

