package com.kareemdev.mycocktailsdb.presentation.ui.fragment.cocktail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.kareemdev.mycocktailsdb.R
import com.kareemdev.mycocktailsdb.databinding.FragmentCocktailBinding
import com.kareemdev.mycocktailsdb.presentation.ui.fragment.alcoholic.AlcoholicFragment
import com.kareemdev.mycocktailsdb.presentation.ui.fragment.nonalcoholic.NonAlcoholicFragment
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [CocktailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class CocktailFragment : Fragment() {

    var _binding: FragmentCocktailBinding? = null
    val binding: FragmentCocktailBinding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCocktailBinding.inflate(inflater, container, false)
        return binding.root
    }

    val listTab = arrayListOf("Alcoholic", "Non-Alcoholic")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<ViewPager2>(R.id.viewPager).adapter = FragmentTypeAdapter(this)

        TabLayoutMediator(
            view.findViewById(R.id.tabs),
            view.findViewById(R.id.viewPager)
        ) { tab, position ->
            tab.text = listTab[position]
        }.attach()
    }

    class FragmentTypeAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            if (position == 0) {
                return AlcoholicFragment()
            } else {
                return NonAlcoholicFragment()
            }
        }
    }


}
