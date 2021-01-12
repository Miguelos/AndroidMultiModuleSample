package me.miguelos.sample.presentation.ui.beers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.miguelos.base.util.imageloader.ImageLoader
import me.miguelos.sample.databinding.ItemBeerBinding
import me.miguelos.sample.presentation.model.Beer


class BeersAdapter(
    private val listener: BeerItemListener,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<BeerViewHolder>() {

    interface BeerItemListener {
        fun onClickedBeer(beer: Beer)
    }

    private val items = ArrayList<Beer>(20)


    init {
        setHasStableIds(true)
    }

    fun addItems(items: ArrayList<Beer>) {
        this.items.addAll(
            items.filter { it !in this.items }
        )
        notifyDataSetChanged()
    }

    fun clear() {
        this.items.clear()
    }

    override fun getItemId(position: Int) =
        if (position in this.items.indices) {
            this.items[position].id
        } else {
            -1
        }

    fun getItem(position: Int): Beer? = if (position in this.items.indices) {
        this.items[position]
    } else {
        null
    }

    override fun getItemViewType(position: Int) = position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val binding: ItemBeerBinding =
            ItemBeerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return BeerViewHolder(binding, listener, imageLoader)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holderPunk: BeerViewHolder, position: Int): Unit =
        holderPunk.bind(items[position])
}
