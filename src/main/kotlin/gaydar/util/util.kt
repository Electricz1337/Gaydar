package gaydar.util

import gaydar.util.LogLevel.*

enum class LogLevel
{
  Off,
  Bug,
  Warning,
  Debug,
  Info
}

class Rolling(private val size: Int) {
  private var total = 0f
  private var index = 0
  private val samples: FloatArray

  val average: Float
    get() = total / size

  init {
    samples = FloatArray(size)
    for (i in 0 until size) samples[i] = 0f
  }

  fun add(x: Float) {
    if (x < 20 && average > 300){ for (i in 0 until size) samples[i] = x; total = x*size; return }
    if (x > 300 && average < 180){ for (i in 0 until size) samples[i] = x; total = x*size; return }
    total -= samples[index]
    samples[index] = x
    total += x
    if (++index == size) index = 0
  }
}


var logLevel = Off

inline fun info(info : () -> String)
{
  if (logLevel.ordinal >= Info.ordinal)
    print(info())
}

inline fun infoln(info : () -> String)
{
  if (logLevel.ordinal >= Info.ordinal)
    println(info())
}

inline fun debugln(info : () -> String)
{
  if (logLevel.ordinal >= Debug.ordinal)
    println(info())
}

inline fun bug(info : () -> String)
{
  if (logLevel.ordinal >= Bug.ordinal)
    print(info())
}

inline fun bugln(info : () -> String)
{
  if (logLevel.ordinal >= Bug.ordinal)
    println(info())
}
