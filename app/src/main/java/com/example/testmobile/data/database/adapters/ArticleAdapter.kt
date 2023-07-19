package com.example.testmobile.data.database.adapters

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testmobile.data.dto.ArticleDTO
import com.example.testmobile.databinding.FragmentHomeItemBinding
import com.example.testmobile.utils.getTimeFromDate

class ArticleAdapter(private val context: Context, private val listener: ArticleAdapterListener) :
    RecyclerView.Adapter<ArticleAdapter.MainViewHolder>() {

    private var articleList = mutableListOf<ArticleDTO>()

    fun setArticles(articleList: List<ArticleDTO>) {
        this.articleList = articleList.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentHomeItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val article = articleList[position]
        if (article.urlToImage!=null) Glide.with(context).load(article.urlToImage).circleCrop().into(holder.binding.image)
        holder.binding.title.text = article.title
        holder.binding.publishedAt.getTimeFromDate(article.publishedAt)

        // Onclick item
        holder.itemView.setOnClickListener {
            listener.onArticleSelected(articleList[position])
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    class MainViewHolder(val binding: FragmentHomeItemBinding) :
        RecyclerView.ViewHolder(binding.root)
}