package com.teegarcs.ime_animation

import android.os.Build
import android.view.View
import android.view.WindowInsets
import androidx.annotation.RequiresApi
import androidx.core.view.doOnLayout

/**
 * Extension function that can be used to add a callback that tries to determine when the keyboard
 * is displayed. This function listens for a layout change from the ViewTreeObserver and uses that
 * as a trigger to check if the keyboard status has changed. This differs from the addKeyboardInsetListener
 * function as this listener does not care about whether the inset of the IME affected the view or not.
 *
 * Use this function over the addKeyboardInsetListener when you always need to know when the keyboard
 * is visible regardless of whether the inset affects your window or not.
 *
 * @param keyboardCallback - function to call with a boolean indicating if keyboard is visible.
 */
@RequiresApi(Build.VERSION_CODES.R)
fun View.addKeyboardListener(keyboardCallback: (visible: Boolean) -> Unit) {
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


/**
 * Extension function that can be used to add a callback to determine when the keyboard
 * is displayed. This function listens window inset changes and uses that
 * as a trigger to check if the keyboard status has changed. This differs from the addKeyboardListener
 * function as this listener only queries for updates when an inset that affects this window happens.
 *
 * Use this function over the addKeyboardListener when you only care about whether the keyboard
 * is visible if the inset of the keyboard affects your window. This means the keyboard could
 * be displayed at times and this callback will not be hit - this would occur in situations such
 * as split screens where the keyboard doesn't cause your view to inset.
 *
 * This function requires the additional calling of
 *  window.setDecorFitsSystemWindows(false)
 *
 * @param keyboardCallback - function to call with a boolean indicating if keyboard is visible.
 */
@RequiresApi(Build.VERSION_CODES.R)
fun View.addKeyboardInsetListener(keyboardCallback: (visible: Boolean) -> Unit) {
    doOnLayout {
        //get init state of keyboard
        var keyboardVisible = rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true

        //callback as soon as the layout is set with whether the keyboard is open or not
        keyboardCallback(keyboardVisible)

        //whenever there is an inset change on the App, check if the keyboard is visible.
        setOnApplyWindowInsetsListener { _, windowInsets ->
            val keyboardUpdateCheck =
                rootWindowInsets?.isVisible(WindowInsets.Type.ime()) == true
            //since the observer is hit quite often, only callback when there is a change.
            if (keyboardUpdateCheck != keyboardVisible) {
                keyboardCallback(keyboardUpdateCheck)
                keyboardVisible = keyboardUpdateCheck
            }

            windowInsets
        }
    }

}
