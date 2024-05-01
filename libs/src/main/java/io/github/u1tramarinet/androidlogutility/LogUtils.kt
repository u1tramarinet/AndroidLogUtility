package io.github.u1tramarinet.androidlogutility

import android.util.Log
import androidx.annotation.VisibleForTesting

@Suppress("MemberVisibilityCanBePrivate", "unused")
object LogUtils {
    fun funIn(message: String? = null) {
        print(Log.INFO, message, "[IN ]")
    }

    fun funOut(message: String? = null) {
        print(Log.INFO, message, "[OUT]")
    }

    fun d(message: String? = null) {
        print(Log.DEBUG, message)
    }

    fun <T> T.withFunOut(format: (result: T) -> String = { result -> "result=$result" }): T {
        funOut(format(this))
        return this
    }

    private fun print(priority: Int, message: String?, prefix: String? = null) {
        val targetElement = getStackTraceFirstElement()
        val tag = acquireTag(targetElement)
        val reference = "(${acquireMethod(targetElement)}@${acquireLink(targetElement)})"
        val msg = listOfNotNull(prefix, message, reference)
            .filter { it.isNotEmpty() }
            .joinToString(separator = " ")
        Log.println(priority, tag, msg)
        callback?.onPrint(priority, tag, msg)
    }

    private fun acquireTag(element: StackTraceElement): String =
        element.className.split(".").last()

    private fun acquireMethod(element: StackTraceElement): String =
        element.methodName

    private fun acquireLink(element: StackTraceElement): String =
        "${element.fileName}:${element.lineNumber}"

    private fun getStackTraceFirstElement(): StackTraceElement {
        val elements = Throwable().stackTrace.toList()
        val ownSimpleName = LogUtils::class.java.simpleName
        return elements.firstOrNull {
            !it.fileName.contains(ownSimpleName)
        } ?: elements.last {
            it.fileName.contains(ownSimpleName)
        }
    }

    private var callback: Callback? = null

    /**
     * Test use only.
     */
    @VisibleForTesting
    internal fun setCallback(callback: Callback?) {
        this.callback = callback
    }

    /**
     * Test use only.
     */
    @VisibleForTesting
    internal interface Callback {
        fun onPrint(priority: Int, tag: String, msg: String)
    }
}