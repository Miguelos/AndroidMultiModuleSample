package me.miguelos.sample.presentation.ui.beers.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import me.miguelos.base.util.imageloader.ImageLoader
import me.miguelos.sample.R
import me.miguelos.sample.databinding.ItemBeerBinding
import me.miguelos.sample.presentation.model.Beer

class BeerViewHolder(
    private val itemBinding: ItemBeerBinding,
    private val listener: BeersAdapter.BeerItemListener,
    private val imageLoader: ImageLoader
) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var beer: Beer

    @SuppressLint("SetTextI18n")
    fun bind(item: Beer) {
        this.beer = item
        itemBinding.listItemNameTv.text = item.name
        item.thumbnail?.let {
            imageLoader.loadImage(
                itemBinding.listItemImageIv,
                it,
                R.drawable.beer_placeholder
            )
        }
        itemBinding.root.setOnClickListener {
            listener.onClickedBeer(beer)
        }
    }
}
