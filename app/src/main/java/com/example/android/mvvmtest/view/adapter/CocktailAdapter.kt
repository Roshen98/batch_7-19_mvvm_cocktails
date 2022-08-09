package com.example.android.mvvmtest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.mvvmtest.databinding.CocktailItemLayoutBinding
import com.example.android.mvvmtest.model.CocktailItem

class CocktailAdapter(private val searchDetails: (String) -> Unit):
    ListAdapter<CocktailItem, CocktailAdapter.CocktailViewHolder>(CocktailItemDiffUtil) {

    class CocktailViewHolder(private val binding: CocktailItemLayoutBinding):
        RecyclerView.ViewHolder(binding.root){

            fun onBind(dataItem: CocktailItem,
            showDetails: (String) -> Unit){
                binding.tvSearchDrinkTag.text =
                    dataItem.strTags
                binding.tvSearchDrinkName.text =
                    dataItem.strDrink
                binding.root.setOnClickListener {
                    showDetails(dataItem.idDrink)
                }
            }

    }
    object CocktailItemDiffUtil: DiffUtil.ItemCallback<CocktailItem>(){
        override fun areItemsTheSame(oldItem: CocktailItem, newItem: CocktailItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: CocktailItem, newItem: CocktailItem): Boolean {
            return oldItem.idDrink == newItem.idDrink
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CocktailViewHolder(
            CocktailItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.onBind( currentList[position], searchDetails)
    }


}