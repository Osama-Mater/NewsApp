package com.osama.newsapp.ui.home

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.osama.domain.model.NewsDomainModel
import com.osama.newsapp.R
import com.osama.newsapp.databinding.NewsItemCardViewBinding
import java.util.*

internal class NewsHomeAdapter : RecyclerView.Adapter<NewsHomeAdapter.NewsViewHolder>() {
    private var _articles: List<NewsDomainModel.Article> = emptyList()

    private var listener: ((NewsDomainModel.Article) -> Unit)? = null

    fun setData(articles: List<NewsDomainModel.Article>) {
        _articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsItemCardViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind(_articles[position])
        holder.itemView.setOnClickListener {
            listener?.invoke(_articles[position])
        }
    }

    override fun getItemCount(): Int = _articles.size

    fun setListener(listener: ((NewsDomainModel.Article) -> Unit)?) {
        this.listener = listener
    }

    internal inner class NewsViewHolder(private val newsItemCardViewBinding: NewsItemCardViewBinding) :
        RecyclerView.ViewHolder(newsItemCardViewBinding.root) {
        fun bind(article: NewsDomainModel.Article) {
            with(newsItemCardViewBinding) {
                articleImage.load(article.urlToImage) {
                    crossfade(true)
                    error(R.drawable.ic_image)
                    fallback(R.drawable.ic_image)
                }
                articleTitle.text = article.title
                publishedTime.text = DateUtils.getRelativeTimeSpanString(
                    article.publishedAt.time,
                    Calendar.getInstance().timeInMillis,
                    DateUtils.DAY_IN_MILLIS
                )
                articleSource.text = article.source.name
            }
        }
    }

}