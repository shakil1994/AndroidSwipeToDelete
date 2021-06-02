package com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.Model.Item
import com.blackbirds.shakil.kotlinswipetodeleterecycleritem.R
import com.squareup.picasso.Picasso

class CartListAdapter(val context: Context, val itemList: MutableList<Item>):
RecyclerView.Adapter<CartListAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_item_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtName.text = itemList[position].name
        holder.txtDescription.text = itemList[position].description
        holder.txtPrice.text = itemList[position].price
        Picasso.get().load(itemList[position].thumbnail).into(holder.imgThumbnail)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun removeItem(position: Int){
        itemList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun restoreItem(item: Item?, position: Int){
        if (item != null){
            itemList.add(position, item)
            notifyItemInserted(position)
        }
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var view_background: RelativeLayout
        var view_foreground: RelativeLayout
        var imgThumbnail: ImageView
        var txtName: TextView
        var txtDescription: TextView
        var txtPrice: TextView

        init {
            view_background = itemView.findViewById(R.id.view_background)
            view_foreground = itemView.findViewById(R.id.view_foreground)
            imgThumbnail = itemView.findViewById(R.id.imgThumbnail)
            txtName = itemView.findViewById(R.id.txtName)
            txtDescription = itemView.findViewById(R.id.txtDescription)
            txtPrice = itemView.findViewById(R.id.txtPrice)
        }
    }
}