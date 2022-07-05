package com.kareemdev.mycocktailsdb.presentation.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kareemdev.mycocktailsdb.data.model.DrinkPreview
import com.kareemdev.mycocktailsdb.databinding.ItemFavoriteBinding

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private val differCallback = object : DiffUtil.ItemCallback<DrinkPreview>() {
        override fun areItemsTheSame(oldItem: DrinkPreview, newItem: DrinkPreview): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: DrinkPreview, newItem: DrinkPreview): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.ViewHolder {
        val view = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder((view))
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.ViewHolder, position: Int) {
        val drink = differ.currentList[position]
        holder.bind(drink)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((DrinkPreview) -> Unit)? = null
    private var onItemDeleteClickListener: ((DrinkPreview) -> Unit)? = null

    inner class ViewHolder(view: ItemFavoriteBinding) : RecyclerView.ViewHolder(view.root) {
        private val image: ImageView = view.ivDrinkImg
        private val title = view.tvDrinkTitle
        private val delete = view.ivDrinkDelete
        fun bind(drink: DrinkPreview) {
            drink.apply {
                title.text = strDrink
                Glide.with(itemView.context)
                    .load(strDrinkThumb)
                    .centerCrop()
                    .into(image)

                delete.setOnClickListener {
                    onItemDeleteClickListener?.let { it(drink) }
                }

                itemView.setOnClickListener {
                    onItemClickListener?.let { it(drink) }
                }
            }
        }
    }

    fun setOnItemClickListener(listener: (DrinkPreview) -> Unit) {
        onItemClickListener = listener
    }

    fun setOnDeleteItemClickListener(listener: (DrinkPreview) -> Unit) {
        onItemDeleteClickListener = listener
    }
}