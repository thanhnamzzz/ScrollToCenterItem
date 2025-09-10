package io.virgo.scrolltocenteritem

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import io.virgo.scrolltocenteritem.databinding.ActivityMainBinding
import lib.virgo.lib_scrollcenteritem.OrientationView
import lib.virgo.lib_scrollcenteritem.callback.OnItemChangedListener
import lib.virgo.lib_scrollcenteritem.callback.ScrollListener
import lib.virgo.lib_scrollcenteritem.callback.ScrollStateChangeListener
import lib.virgo.lib_scrollcenteritem.transform.ScaleTransformer

class MainActivity : AppCompatActivity(),
    OnItemChangedListener<ForecastAdapter.ViewHolder>,
    ScrollStateChangeListener<ForecastAdapter.ViewHolder>,
    IForecastListener {
    private lateinit var binding: ActivityMainBinding
    private val foreCasts = WeatherStation.get().forecasts
    private var positionClick = -1
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
            /** Bắt buộc thêm vào */
            adapter = ForecastAdapter(foreCasts, this@MainActivity)
            setScrollStateChangeListener(this@MainActivity)

            /** Có thể không cần thêm */
            setOnItemChangedListener(this@MainActivity)
            scrollToPosition(3)
            setItemTransitionTimeMillis(150) //Cài đặt thời gian animation chuyển đổi item
            setItemTransformer(ScaleTransformer.Builder().setMinScale(0.5f).build()) //Cài đặt hiệu ứng chuyển đổi cho các item ngoài
//            setOrientation(OrientationView.VERTICAL) //hoặc OrientationView.HORIZONTAL
//            setOffscreenItems(1) //Dành thêm không gian bằng (childSize * count) ở mỗi bên của chế độ xem (không hữu dụng lắm)
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
        if (positionClick == binding.cityPicker.getCurrentItem()) {
            Log.i("Namzzz", "MainActivity: onScrollEnd DONE $positionClick")
            positionClick = -1
        }
    }

    override fun onScroll(
        scrollPosition: Float,
        currentHolder: ForecastAdapter.ViewHolder,
        newCurrent: ForecastAdapter.ViewHolder,
    ) {}

    override fun onItemClick(position: Int) {
        positionClick = position
        if (positionClick == binding.cityPicker.getCurrentItem()) {
            Log.i("Namzzz", "MainActivity: onItemClick DONE $positionClick")
            positionClick = -1
        }
    }
}