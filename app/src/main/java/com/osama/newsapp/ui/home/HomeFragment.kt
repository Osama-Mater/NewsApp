package com.osama.newsapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.osama.newsapp.R
import com.osama.newsapp.databinding.FragmentHomeBinding
import com.osama.newsapp.ui.home.ArticleDetailsFragment.Companion.ARTICLE
import com.osama.newsapp.ui.home.ArticleDetailsFragment.Companion.ARTICLE_DETAILS_BUNDLE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private val newsHomeAdapter: NewsHomeAdapter = NewsHomeAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            setHasFixedSize(true)
            adapter = newsHomeAdapter
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.viewState.collect { homeState ->
                    if (homeState.newsList.articles.isNotEmpty()) {
                        newsHomeAdapter.setData(homeState.newsList.articles)
                    }
                }
            }
        }
        newsHomeAdapter.setListener {
            setFragmentResult(ARTICLE_DETAILS_BUNDLE, bundleOf(ARTICLE to it))
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ArticleDetailsFragment.newInstance())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}