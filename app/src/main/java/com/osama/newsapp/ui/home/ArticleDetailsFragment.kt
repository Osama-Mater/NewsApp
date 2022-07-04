package com.osama.newsapp.ui.home

import android.os.Bundle
import android.text.Html
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import coil.load
import com.osama.domain.model.NewsDomainModel
import com.osama.newsapp.R
import com.osama.newsapp.databinding.FragmentArticleDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var article: NewsDomainModel.Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(ARTICLE_DETAILS_BUNDLE) { requestKey, bundle ->
            article = bundle.getParcelable(ARTICLE)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        binding.materialToolbar.setNavigationOnClickListener {
            this@ArticleDetailsFragment.requireActivity().onBackPressed()
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            articleImage.load(article.urlToImage) {
                crossfade(true)
                error(R.drawable.ic_image)
                fallback(R.drawable.ic_image)
            }
            articleTitle.text = article.title
            articleAuthor.text = article.author
            articleSource.text = article.source.name
            articlePublishedTime.text = DateUtils.getRelativeTimeSpanString(
                article.publishedAt.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.DAY_IN_MILLIS
            )
            articleDescription.text =
                Html.fromHtml(article.description, Html.FROM_HTML_MODE_COMPACT)
            articleContent.text = Html.fromHtml(article.content, Html.FROM_HTML_MODE_COMPACT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val ARTICLE_DETAILS_BUNDLE = "ArticleDetailsFragmentBundle"
        const val ARTICLE = "Article"
        fun newInstance(): ArticleDetailsFragment {
            return ArticleDetailsFragment()
        }
    }
}