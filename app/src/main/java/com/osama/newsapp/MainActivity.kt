package com.osama.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.osama.newsapp.databinding.ActivityMainBinding
import com.osama.newsapp.ui.home.HomeFragment
import com.osama.newsapp.ui.notifications.NotificationsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pager: ViewPager2 = binding.pager

        val pagerAdapter = PagerAdapter(
            listOf(HomeFragment(), NotificationsFragment()), supportFragmentManager, lifecycle
        )
        pager.adapter = pagerAdapter
    }
}