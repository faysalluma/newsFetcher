package com.example.testmobile.presentation.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testmobile.data.dto.ArticleDTO
import com.example.testmobile.databinding.FragmentHomeItemBinding
import com.example.testmobile.utils.getTimeFromDate

class ArticleAdapter(private val context: Context, private val listener: ArticleAdapterListener)  : RecyclerView.Adapter<ArticleAdapter.ViewHolder>(){
    private lateinit var binding: FragmentHomeItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder {
        binding= FragmentHomeItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)

        // Onclick item
        holder.itemView.setOnClickListener {
            listener.onArticleSelected(differ.currentList[position])
        }
    }

    override fun getItemCount()=differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("NewApi")
        fun setData(article : ArticleDTO){
            binding.apply {
                if (article.urlToImage!=null) Glide.with(context).load(article.urlToImage).circleCrop().into(image)
                title.text = article.title
                publishedAt.getTimeFromDate(article.publishedAt)
            }
        }

    }

    private val differCallback = object : DiffUtil.ItemCallback<ArticleDTO>(){
        override fun areItemsTheSame(oldItem: ArticleDTO, newItem: ArticleDTO): Boolean {
            return  oldItem.title == newItem.title
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ArticleDTO, newItem: ArticleDTO): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this,differCallback)

}