package io.github.u1tramarinet.androidlogutility

class LogArgs {
    companion object {
        fun arg(name: String = "", value: Any?): List<LogArg> {
            return listOf(LogArg(name, value))
        }
    }
}

fun List<LogArg>.arg(name: String = "", value: Any?): List<LogArg> {
    return this + LogArgs.arg(name, value)
}