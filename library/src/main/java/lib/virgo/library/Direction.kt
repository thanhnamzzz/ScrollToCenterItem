package lib.virgo.library

/**
 * Created by yarolegovich on 16.03.2017.
 */
internal enum class Direction {
    START {
        override fun applyTo(delta: Int): Int {
            return delta * -1
        }
    },
    END {
        override fun applyTo(delta: Int): Int {
            return delta
        }
    };

    abstract fun applyTo(delta: Int): Int

    companion object {
        @JvmStatic
        fun fromDelta(delta: Int): Direction {
            return if (delta > 0) END else START
        }
    }
}
