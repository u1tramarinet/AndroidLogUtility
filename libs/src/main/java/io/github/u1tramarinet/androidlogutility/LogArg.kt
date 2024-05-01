package io.github.u1tramarinet.androidlogutility

class LogArg(private val name: String = "", private val value: Any?) {
    internal fun format(): String {
        return listOf(name, (value ?: "null"))
            .filter { it.toString().isNotEmpty() }
            .joinToString(separator = "=")
    }
}