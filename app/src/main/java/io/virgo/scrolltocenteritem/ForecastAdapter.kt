package io.virgo.scrolltocenteritem

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

/**
 * Created by yarolegovich on 08.03.2017.
 */
class ForecastAdapter(private val data: MutableList<Forecast>, private val listener: IForecastListener) :
	RecyclerView.Adapter<ForecastAdapter.ViewHolder>() {
	private var parentRecycler: RecyclerView? = null

	override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
		super.onAttachedToRecyclerView(recyclerView)
		parentRecycler = recyclerView
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val v = inflater.inflate(R.layout.item_city_card, parent, false)
		return ViewHolder(v)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val iconTint = ContextCompat.getColor(holder.itemView.context, R.color.grayIconTint)
		val forecast = data[position]
		Glide.with(holder.itemView.context)
			.load(forecast.cityIcon) //                .listener(new TintOnLoad(holder.imageView, iconTint))
			.listener(object : RequestListener<Drawable?> {
				override fun onLoadFailed(
					e: GlideException?,
					model: Any?,
					target: Target<Drawable?>?,
					isFirstResource: Boolean,
				): Boolean {
					return false
				}

				override fun onResourceReady(
					resource: Drawable?,
					model: Any?,
					target: Target<Drawable?>?,
					dataSource: DataSource?,
					isFirstResource: Boolean,
				): Boolean {
					holder.imageView.setColorFilter(iconTint)
					return false
				}
			})
			.into(holder.imageView)
		holder.textView.text = forecast.cityName
	}

	override fun getItemCount(): Int {
		return data.size
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
		View.OnClickListener {
		val imageView: ImageView = itemView.findViewById(R.id.city_image)
		val textView: TextView = itemView.findViewById(R.id.city_name)

		init {
			itemView.findViewById<View>(R.id.container).setOnClickListener(this)
		}

		fun showText() {
			val parentHeight = (imageView.parent as View).height
			val scale = (parentHeight - textView.height) / imageView.height.toFloat()
			imageView.pivotX = imageView.width * 0.5f
			imageView.pivotY = 0f
			imageView.animate().scaleX(scale)
				.withEndAction {
					textView.visibility = View.VISIBLE
					imageView.setColorFilter(Color.BLACK)
				}
				.scaleY(scale).setDuration(200)
				.start()
		}

		fun hideText() {
			imageView.setColorFilter(
				ContextCompat.getColor(
					imageView.context,
					R.color.grayIconTint
				)
			)
			textView.visibility = View.INVISIBLE
			imageView.animate().scaleX(1f).scaleY(1f)
				.setDuration(200)
				.start()
		}

		override fun onClick(v: View?) {
			parentRecycler!!.smoothScrollToPosition(layoutPosition)
			listener.onItemClick(layoutPosition)
		}
	}
}
