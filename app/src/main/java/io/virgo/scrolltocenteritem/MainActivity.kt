package io.virgo.scrolltocenteritem

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.virgo.scrolltocenteritem.databinding.ActivityMainBinding
import lib.virgo.lib_scrollcenteritem.callback.OnItemChangedListener
import lib.virgo.lib_scrollcenteritem.callback.ScrollStateChangeListener
import lib.virgo.lib_scrollcenteritem.transform.ScaleTransformer

class MainActivity : AppCompatActivity(),
    OnItemChangedListener<ForecastAdapter.ViewHolder>,
    ScrollStateChangeListener<ForecastAdapter.ViewHolder> {
    private lateinit var binding: ActivityMainBinding
    private val foreCasts = WeatherStation.get().forecasts
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.cityPicker.apply {
            adapter = ForecastAdapter(foreCasts)
            setOnItemChangedListener(this@MainActivity)
            setScrollStateChangeListener(this@MainActivity)
            scrollToPosition(1)
            setItemTransitionTimeMillis(150)
            setItemTransformer(ScaleTransformer.Builder().setMinScale(0.5f).build())
        }
    }

    override fun onCurrentItemChanged(
        viewHolder: ForecastAdapter.ViewHolder,
        adapterPosition: Int,
    ) {
        viewHolder.showText()
    }

    override fun onScrollStart(
        currentItemHolder: ForecastAdapter.ViewHolder,
        adapterPosition: Int,
    ) {
        currentItemHolder.hideText()
    }

    override fun onScrollEnd(
        currentItemHolder: ForecastAdapter.ViewHolder,
        adapterPosition: Int,
    ) {
        Log.d("Namzzz", "MainActivity: onScrollEnd")
    }

    override fun onScroll(
        scrollPosition: Float,
        currentHolder: ForecastAdapter.ViewHolder,
        newCurrent: ForecastAdapter.ViewHolder,
    ) {
        Log.d("Namzzz", "MainActivity: onScroll")
    }
}