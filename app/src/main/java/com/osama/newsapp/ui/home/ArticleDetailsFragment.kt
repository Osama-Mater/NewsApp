package com.osama.newsapp.ui.home

import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.format.DateUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.browser.customtabs.CustomTabsIntent
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
            articleTitle.text =
                article.title.replaceAfter('-', "").replaceFirst('-', ' ', true)
            articleAuthor.text = article.author
            articleSource.text = article.source.name
            articlePublishedTime.text = DateUtils.getRelativeTimeSpanString(
                article.publishedAt.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.DAY_IN_MILLIS
            )
            articleDescription.text =
                Html.fromHtml(article.description, Html.FROM_HTML_MODE_COMPACT)

            articleContent.movementMethod = LinkMovementMethod.getInstance()
            articleContent.setText(makeLinks(), TextView.BufferType.SPANNABLE)
        }
    }

    private fun makeLinks(): SpannableString {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(ds: TextPaint) {
                ds.color = R.color.purple_700     // setup color
                ds.isUnderlineText = false // remove underline
            }

            override fun onClick(view: View) {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(requireContext(), Uri.parse(article.url))

            }
        }

        val stringSpan = SpannableString(article.content)
        if (stringSpan.isNotEmpty()) {
            article.content?.let {
                stringSpan.setSpan(
                    clickableSpan,
                    it.indexOf('['),
                    it.indexOf(']'),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
        return stringSpan
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