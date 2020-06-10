package org.dipalme.proteApp.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.dipalme.proteApp.R
import org.dipalme.proteApp.model.News

class HomeAdapter(private val data: List<News>, context: Context) :
    RecyclerView.Adapter<HomeAdapter.Holder>() {

    private val mContext = context
    private lateinit var dataURL: String
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_home, parent, false)
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val news: News = data[position]

        holder.title.text = news.title
        dataURL = news.url
        Glide.with(mContext).load(news.image).into(holder.imageView)

        holder.imageView.setOnClickListener {
            val i = Intent(mContext, HomeWebView::class.java)
            i.putExtra("url", data[position].url)
            ContextCompat.startActivity(mContext, i, null)
        }

        holder.title.setOnClickListener {
            val i = Intent(mContext, HomeWebView::class.java)
            i.putExtra("url", data[position].url)
            ContextCompat.startActivity(mContext, i, null)
        }
    }

    class Holder(v: View) : RecyclerView.ViewHolder(v) {
        val imageView: ImageView = itemView.findViewById(R.id.ivImage)
        val title: TextView = itemView.findViewById(R.id.tvName)
    }
}