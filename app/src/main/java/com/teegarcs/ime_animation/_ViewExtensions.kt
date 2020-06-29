package com.teegarcs.ime_animation

import android.os.Build
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.core.view.doOnLayout

/**
 * Extension function that can be used to add a callback that tries to determine when the keyboard
 * is displayed. This function listens for a layout change from the ViewTreeObserver and uses that
 * as a trigger to check if the keyboard status has changed.
 *
 * @param keyboardCallback - function to call with a boolean indicating if keyboard is visible.
 */
@RequiresApi(Build.VERSION_CODES.R)
fun View.addKeyboardWatcher(keyboardCallback: (visible: Boolean) -> Unit) {
    doOnLayout {
        //get init state of keyboard
        var keyboardVisible = rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true

        //callback as soon as the layout is set with whether the keyboard is open or not
        keyboardCallback(keyboardVisible)

        //whenever the layout resizes/changes, callback with the state of the keyboard.
        viewTreeObserver.addOnGlobalLayoutListener {
            val keyboardUpdateCheck = rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true
            //since the observer is hit quite often, only callback when there is a change.
            if (keyboardUpdateCheck != keyboardVisible) {
                keyboardCallback(keyboardUpdateCheck)
                keyboardVisible = keyboardUpdateCheck
            }
        }
    }
}
