package com.binar.secondhand.core.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.binar.secondhand.R
import com.binar.secondhand.core.data.Equatable
import com.binar.secondhand.core.event.StateEvent
import com.binar.secondhand.core.event.StateEventManager
import com.binar.secondhand.databinding.ItemAdapterErrorBinding
import com.binar.secondhand.screen.home.adapter.GenericAdapter

fun ViewGroup.inflateRoot(@LayoutRes layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

fun <T : Equatable> genericAdapterLazy(
    layoutRes: Int,
    loadingRes: Int = R.layout.item_adapter_loading,
    errorRes: Int = R.layout.item_adapter_error,
    onBind: View.(position: Int, item: T) -> Unit,
    onBindError: View.(String) -> Unit = {}
): Lazy<GenericAdapter<T>> {
    return lazy {

        val adapter = GenericAdapter<T>(
            layoutRes = layoutRes,
            loadingRes = loadingRes,
            errorRes = errorRes
        )
        adapter.onBind = onBind

        if (errorRes == R.layout.item_adapter_error) {
            adapter.onBindError = { message ->
                ItemAdapterErrorBinding.bind(this).apply {
                    itemTvError.text = message
                }
            }
        } else {
            adapter.onBindError = onBindError
        }

        return@lazy adapter
    }
}

fun RecyclerView.onReachBottomScroll(
    stateEventManager: StateEventManager<*>,
    action: () -> Unit
) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val isSettling = newState == RecyclerView.SCROLL_STATE_SETTLING
            val isBottomReach = !canScrollVertically(1)

            val valueEvent = stateEventManager.value
            val isEventSuccess = valueEvent is StateEvent.Success
            if (isEventSuccess && isSettling && isBottomReach) {
                action.invoke()
            }
        }
    })
}