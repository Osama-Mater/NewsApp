package com.osama.newsapp.di

import com.osama.domain.usecase.GetNews
import com.osama.newsapp.ui.home.HomeViewModel
import com.osama.newsapp.ui.notifications.NotificationsViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
class ViewModelsModule {

    @Provides
    fun provideHomeViewModel(
        getNews: GetNews,
    ) = HomeViewModel(getNews)

    @Provides
    fun provideNotificationsViewModel() = NotificationsViewModel()
}
