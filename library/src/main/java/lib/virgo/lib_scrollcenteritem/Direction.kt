package lib.virgo.lib_scrollcenteritem

/**
 * Rebuilt from [DroidSky/DiscreteScrollView](https://github.com/DroidSky/DiscreteScrollView)
 */
enum class Direction {
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
