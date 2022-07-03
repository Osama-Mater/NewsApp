package com.osama.newsapp.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.osama.domain.model.NewsDomainModel
import com.osama.domain.usecase.GetNews
import com.osama.newsapp.base.BaseViewModel
import com.osama.newsapp.base.ViewEvent
import com.osama.newsapp.base.ViewSideEffect
import com.osama.newsapp.base.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getNews: GetNews) :
    BaseViewModel<HomeViewEvent, HomeUiState, HomeSideEffect>() {

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        Log.e("HomeViewModel", "errorHandler:", exception)
        setState {
            copy(
                isLoading = false,
                isError = true
            )
        }
    }

    init {
        setEvent(HomeViewEvent.GetNews)
    }

    override fun setInitialState(): HomeUiState = HomeUiState(
        newsList = NewsDomainModel(status = "", totalResults = -1, emptyList()),
        isLoading = true,
        isError = false
    )

    override fun handleEvents(event: HomeViewEvent) {
        when (event) {
            is HomeViewEvent.GetNews -> {
                viewModelScope.launch(errorHandler) {
                    getNews.execute().catch { throwable ->
                        handleExceptions(throwable)
                    }.collect {
                        setState {
                            copy(
                                newsList = it,
                                isLoading = false,
                                isError = false
                            )
                        }
                    }

                }
            }
        }
    }

    private fun handleExceptions(throwable: Throwable) {
        Log.e("HomeViewModel", "handleExceptions:", throwable)
        setState {
            copy(
                isLoading = false,
                isError = true
            )
        }
    }
}

sealed class HomeViewEvent : ViewEvent {
    object GetNews : HomeViewEvent()
}

data class HomeUiState(
    val newsList: NewsDomainModel,
    val isLoading: Boolean = false,
    val isError: Boolean = false
) : ViewState

sealed class HomeSideEffect : ViewSideEffect
