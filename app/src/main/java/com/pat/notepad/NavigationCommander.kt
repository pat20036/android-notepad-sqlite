package com.pat.notepad

import android.app.Application
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.NavController

interface NavigationCommander {
    fun restart(graphId: Int)
    fun popBackStack()
    fun navigate(destinationId: Int)
}

class NavigationCommanderImpl(
    private val application: Application,
    private val getNavController: () -> NavController?
) : NavigationCommander, LifecycleObserver {

    private var isAppInForeground = false

    private val pendingActions = mutableListOf<() -> Any?>()

    private val graphName
        get() = getNavController()?.graph?.id?.resourceName

    private val currentDestinationName
        get() = getNavController()?.currentDestination?.id?.resourceName

    private val Int.resourceName
        get() = let(application.resources::getResourceName).removePrefix("com.pat.notepad")

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppInForeground() {
        isAppInForeground = true
        pendingActions.run {
            forEach { it.invoke() }
            clear()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppInBackground() {
        isAppInForeground = false
    }

    override fun restart(graphId: Int) {
        getNavController()?.setGraph(graphId)
    }

    override fun navigate(destinationId: Int) = commit {
        getNavController()?.safeNavigate(destinationId)
    }

    override fun popBackStack() = commit {
        getNavController()?.run {
            popBackStack()
        }
    }

    private fun NavController.safeNavigate(destinationId: Int) {
        try {
            navigate(destinationId)
        } catch (e: IllegalArgumentException) {
            Log.d("Navigation Commander", "NavController.safeNavigate")
        }
    }

    private fun commit(action: () -> Any?) {
        if (isAppInForeground) {
            action.invoke()
        } else {
            pendingActions.add(action)
        }
    }
}