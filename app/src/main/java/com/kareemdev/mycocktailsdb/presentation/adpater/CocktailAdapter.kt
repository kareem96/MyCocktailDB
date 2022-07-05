package com.kareemdev.mycocktailsdb.presentation.adpater

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kareemdev.mycocktailsdb.data.model.DrinkPreview
import com.kareemdev.mycocktailsdb.databinding.ItemCocktailBinding

class CocktailAdapter : RecyclerView.Adapter<CocktailAdapter.ViewHolder>(){
    private var onItemClickListener: ((DrinkPreview) -> Unit)? = null


    private val diffCallback = object : DiffUtil.ItemCallback<DrinkPreview>(){
        override fun areItemsTheSame(oldItem: DrinkPreview, newItem: DrinkPreview): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

        override fun areContentsTheSame(oldItem: DrinkPreview, newItem: DrinkPreview): Boolean {
            return oldItem == newItem
        }

    }
    val diff = AsyncListDiffer(this, diffCallback)

    inner class ViewHolder(view: ItemCocktailBinding): RecyclerView.ViewHolder(view.root) {
        private val image: ImageView = view.ivDrinkImg
        private val title = view.tvDrinkTitle
        fun bind(drink: DrinkPreview){
            drink.apply {
                title.text = strDrink
                Glide.with(itemView.context)
                    .load(strDrinkThumb)
                    .into(image)
                itemView.setOnClickListener {
                    onItemClickListener?.let { it(drink) }
                }
            }
        }

    }

    fun setOnItemClickListener(listener: (DrinkPreview) -> Unit){
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailAdapter.ViewHolder {
        val view = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CocktailAdapter.ViewHolder, position: Int) {
        val drink = diff.currentList[position]
        holder.bind(drink)
    }

    override fun getItemCount(): Int {
        return diff.currentList.size
    }
}