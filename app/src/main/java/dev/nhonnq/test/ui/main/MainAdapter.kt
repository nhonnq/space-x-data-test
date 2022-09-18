package dev.nhonnq.test.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dev.nhonnq.test.Constants
import dev.nhonnq.test.base.adapter.BaseAdapter
import dev.nhonnq.test.base.adapter.BaseHolder
import dev.nhonnq.test.data.db.entity.Upcoming
import dev.nhonnq.test.databinding.ItemUpcomingBinding
import dev.nhonnq.test.utils.DateTimeUtil.getTimeFormatUtc

class MainAdapter(
    private val context: Context,
    inflater: LayoutInflater,
    dataSource: ArrayList<Upcoming>?
) : BaseAdapter<Upcoming, BaseHolder<Upcoming>>(inflater, dataSource) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseHolder<Upcoming> {
        val layoutInflater = LayoutInflater.from(context)
        val binding = ItemUpcomingBinding.inflate(layoutInflater, parent, false)
        return ItemHolder(binding)
    }

    inner class ItemHolder(private val binding: ItemUpcomingBinding) :
        BaseHolder<Upcoming>(binding.root) {

        @SuppressLint("SetTextI18n")
        override fun bind(data: Upcoming, position: Int) {
            super.bind(data, position)

            data.let {
                binding.txtFlightNumber.text = "Flight ${data.flightNumber}"
                binding.txtFlightName.text = data.name
                binding.txtDate.text = data.dateUtc?.let { time -> getTimeFormatUtc(time) }

                Glide.with(context)
                    .load(data.links?.patch?.small ?: Constants.DEFAULT_FLIGHT_IMAGE_URL)
                    .dontAnimate()
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.imgFlight)
            }
        }
    }

}