package com.osama.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import com.osama.domain.model.NewsDomainModel
import com.osama.newsapp.databinding.FragmentArticleDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailsFragment : Fragment() {

    private var _binding: FragmentArticleDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener(ARTICLE_DETAILS_BUNDLE) { requestKey, bundle ->
            val result: NewsDomainModel.Article? = bundle.getParcelable(ARTICLE)
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