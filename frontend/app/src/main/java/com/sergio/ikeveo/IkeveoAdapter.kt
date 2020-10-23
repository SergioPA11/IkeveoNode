package com.sergio.ikeveo

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.sergio.ikeveo.models.Gafa
import com.squareup.picasso.Picasso
import java.net.URL


class IkeveoAdapter (var gafaList: ArrayList<Gafa>, val context: Context) : RecyclerView.Adapter<IkeveoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IkeveoAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.gafa_list_row, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return gafaList.size;
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(gafaList[position], context)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(g: Gafa, context: Context){
            val url = "http://192.168.1.39:8080/img/gafas-"
            val txt_brand: TextView = itemView.findViewById(R.id.textViewBrand)
            val txt_model: TextView = itemView.findViewById(R.id.textViewModel)
            val img: ImageView = itemView.findViewById(R.id.imageViewGafaDetail)

            txt_brand.text = g.brand
            txt_model.text = g.model

            val imageUrl = url + g.id.toString() + ".jpg"
            Picasso.with(context).load(imageUrl).into(img);

            itemView.setOnClickListener {
                val intent = Intent(context, IkeveoDetailActivity::class.java)
                intent.putExtra("gafaId", g.id)
                intent.putExtra("state", "Showing")
                Log.v("hola caracola antes", g.id.toString())
                context.startActivity(intent)
            }
        }
    }
}