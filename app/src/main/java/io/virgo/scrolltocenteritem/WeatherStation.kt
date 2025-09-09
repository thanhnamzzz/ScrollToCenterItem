package io.virgo.scrolltocenteritem

/**
 * Created by yarolegovich on 08.03.2017.
 */
class WeatherStation private constructor() {
	val forecasts: MutableList<Forecast>
		get() = mutableListOf(
            Forecast("Pisa", R.mipmap.pisa, "16", Weather.PARTLY_CLOUDY),
            Forecast("Paris", R.mipmap.paris, "14", Weather.CLEAR),
            Forecast("New York", R.mipmap.new_york, "9", Weather.MOSTLY_CLOUDY),
            Forecast("Rome", R.mipmap.rome, "18", Weather.PARTLY_CLOUDY),
            Forecast("London", R.mipmap.london, "6", Weather.PERIODIC_CLOUDS),
            Forecast("Washington", R.mipmap.washington, "20", Weather.CLEAR)
		)

	companion object {
		fun get(): WeatherStation {
			return WeatherStation()
		}
	}
}
