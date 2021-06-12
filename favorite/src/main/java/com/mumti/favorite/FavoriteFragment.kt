package com.mumti.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mumti.jetpacksubmission.databinding.FragmentFavoriteBinding
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class FavoriteFragment : Fragment() {

    private lateinit var favoriteBinding: FragmentFavoriteBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoriteBinding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return favoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadKoinModules(favoriteModule)

        val sectionPagerAdapter =
            context?.let { FavoriteSectionPagerAdapter(it, childFragmentManager) }
        favoriteBinding.viewPager.adapter = sectionPagerAdapter
        favoriteBinding.tabs.setupWithViewPager(favoriteBinding.viewPager)

        unloadKoinModules(favoriteModule)
    }
}