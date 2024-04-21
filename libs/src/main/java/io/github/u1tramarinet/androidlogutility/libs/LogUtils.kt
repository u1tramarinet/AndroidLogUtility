package io.github.u1tramarinet.androidlogutility.libs

import android.util.Log

@Suppress("MemberVisibilityCanBePrivate", "unused")
object LogUtils {
    fun funIn(message: String? = null) {
        print(Log.INFO, message)
    }

    fun funEnd(message: String? = null) {
        print(Log.INFO, message)
    }

    fun d(message: String? = null) {
        print(Log.DEBUG, message)
    }

    fun <T> T.withFunEnd(format: (result: T) -> String = { result -> "result=$result" }): T {
        funEnd(format(this))
        return this
    }

    private fun print(priority: Int, message: String?, prefix: String? = null) {
        val targetElement = getStackTraceFirstElement()
        val tag = acquireTag(targetElement)
        val link = acquireLink(targetElement)
        val prf = if (prefix != null) "$prefix " else ""
        val msg = "$prf$message ($link)"
        Log.println(priority, tag, msg)
    }

    private fun acquireTag(element: StackTraceElement): String = element.className

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
}