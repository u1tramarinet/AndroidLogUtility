package io.github.u1tramarinet.androidlogutility

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import org.junit.Assert.*

/**
 * 出力内容確認用.
 */
@RunWith(AndroidJUnit4::class)
class LogUtilsTest {

    @Test
    fun testFunIn() {
        assertLogLevel(Log.INFO) {
            LogUtils.funIn()
        }
        assertLogLevel(Log.INFO) {
            LogUtils.funIn(message = "message")
        }
    }

    @Test
    fun testFunOut() {
        assertLogLevel(Log.INFO) {
            LogUtils.funOut()
        }
        assertLogLevel(Log.INFO) {
            LogUtils.funOut(message = "message")
        }
    }

    @Test
    fun testD() {
        assertLogLevel(Log.DEBUG) {
            LogUtils.d()
        }
        assertLogLevel(Log.DEBUG) {
            LogUtils.d(message = "message")
        }
    }

    private fun assertLogLevel(expected: Int, action: () -> Unit) {
        val latch = CountDownLatch(1)
        val callback: LogUtils.Callback = object : LogUtils.Callback {
            override fun onPrint(priority: Int, tag: String, msg: String) {
                println("priority=$priority, tag=$tag, msg=$msg")
                assertEquals(expected, priority)
                latch.countDown()
            }
        }
        LogUtils.setCallback(callback)
        action()
        latch.await()
    }
}