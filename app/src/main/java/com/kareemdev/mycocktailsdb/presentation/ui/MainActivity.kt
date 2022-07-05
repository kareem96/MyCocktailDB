package com.kareemdev.mycocktailsdb.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kareemdev.mycocktailsdb.R
import com.kareemdev.mycocktailsdb.databinding.ActivityMainBinding
import com.kareemdev.mycocktailsdb.presentation.ui.fragment.cocktail.CocktailFragment
import com.kareemdev.mycocktailsdb.presentation.ui.fragment.favorite.FavoriteFragment
import com.kareemdev.mycocktailsdb.presentation.ui.fragment.search.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newNavHostFragment = supportFragmentManager.findFragmentById(R.id.drinksNavHostFragment) as NavHostFragment
        binding.bottomNavigationView.setupWithNavController(newNavHostFragment.findNavController())

    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}