package com.example.beerapp.screen.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beerapp.R
import com.example.beerapp.databinding.BeerItemBinding
import com.example.beerapp.model.canon.Beer
import com.squareup.picasso.Picasso

class MainAdapter (var beerList: MutableList<Beer>?,
                   val itemClicked: OnItemClicked
) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    interface OnItemClicked {
        fun itemClicked(beer: Beer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.beer_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(beerList.isNullOrEmpty()){
            return 0
        }
        return beerList!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if(!beerList.isNullOrEmpty()) {
            holder.bindView(beerList!![position])
        }
    }

    fun updateBeerList(updatedBeerList: MutableList<Beer>){
        for (beer in updatedBeerList) {
            beerList?.add(beer)
        }
        notifyDataSetChanged()
    }

    fun clearMainAdapter() {
        beerList?.run {
            beerList!!.clear()
            notifyDataSetChanged()
        }
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        private val binding  = BeerItemBinding.bind(view)

        init{
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            beerList?.get(adapterPosition)?.let { itemClicked.itemClicked(it) }
        }

        fun bindView(beer: Beer) {
            binding.apply {
                beer.image?.run {
                    Picasso.with(binding.root.context)
                        .load(beer.image)
                        .error(R.drawable.ic_baseline_error_24)
                        .into(beerImageView)
                }
                beer.name?.let {  nameTextView.text = beer.name }
            }
        }
    }
}