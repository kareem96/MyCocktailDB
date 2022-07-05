package com.kareemdev.mycocktailsdb.presentation.ui.fragment.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View

import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.kareemdev.mycocktailsdb.R
import com.kareemdev.mycocktailsdb.data.model.Drink
import com.kareemdev.mycocktailsdb.databinding.FragmentDetailBinding
import com.kareemdev.mycocktailsdb.utils.ConnectionLiveData
import com.kareemdev.mycocktailsdb.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [DetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val TAG = "Detail"
    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()
    private lateinit var connectionLiveData: ConnectionLiveData
    val args: DetailFragmentArgs by navArgs()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        requireActivity().title = "Drink"
        (activity as AppCompatActivity).supportActionBar?.let {
            it.setHomeButtonEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.setHomeAsUpIndicator(R.drawable.ic__back)
        }
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val drink = args.drink

        connectionLiveData = ConnectionLiveData(this.requireContext())
        connectionLiveData.observe(viewLifecycleOwner){ isAvailable ->
            if(isAvailable){
                setupDetailSearch()
                viewModel.getDrinkDetail(drink.idDrink)
                Glide.with(this)
                    .load(drink.strDrinkThumb)
                    .into(binding.ivDrinkDetail)
                binding.floatingActionButton.setOnClickListener {
                    viewModel.favoriteCocktail(drink)
                    Snackbar.make(view, "Drink saved successfully", Snackbar.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(
                    this.requireContext(),
                    "No Internet Connection Available!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            binding.tvTitleDetail.text = drink.strDrink

        }
    }

    private fun setupDetailSearch() {
        viewModel.drinkLiveData.observe(viewLifecycleOwner){ responseEvent ->
            val response = responseEvent.peekContent()
            when(response){
                is Resource.Success ->{
                    hideProgressBar()
                    response.data?.let { drinkResponse ->
                        bindItemDetails(drinkResponse.drinks[0])
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }

        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                findNavController().navigateUp()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindItemDetails(drink: Drink) {
        binding.apply {
            tvTitleDetail.text = drink.strDrink
            tvIngredients.text = checkIngredients(drink)
            tvTitleDetail.text = drink.strDrink
            tvTitleDetail.text = drink.strDrink
        }

    }

    private fun checkMeasure(drink: Drink): String {
        var drinksMeasure = ""

        drink.apply {

            if (!strMeasure1.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure1" else if (!strIngredient1.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strMeasure2.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure2" else if (!strIngredient2.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strMeasure3.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure3" else if (!strIngredient3.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strMeasure4.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure4" else if (!strIngredient4.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strMeasure5.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure5" else if (!strIngredient5.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strMeasure6.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure6" else if (!strIngredient6.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strMeasure7.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure7" else if (!strIngredient7.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strMeasure8.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure8" else if (!strIngredient8.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strMeasure9.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure9" else if (!strIngredient9.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strMeasure10.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure10" else if (!strIngredient10.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strIngredient11.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure11" else if (!strIngredient11.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            if (!strIngredient12.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet} $strMeasure12" else if (!strIngredient12.isNullOrBlank()) drinksMeasure += "\n ${Typography.bullet}  -"
            return drinksMeasure
        }

    }

    private fun checkIngredients(drink: Drink): String {
        var drinksIngredients = ""

        drink.apply {
            if (!strIngredient1.isNullOrBlank()) drinksIngredients += "\n $strIngredient1"
            if (!strIngredient2.isNullOrBlank()) drinksIngredients += "\n $strIngredient2"
            if (!strIngredient3.isNullOrBlank()) drinksIngredients += "\n $strIngredient3"
            if (!strIngredient4.isNullOrBlank()) drinksIngredients += "\n $strIngredient4"
            if (!strIngredient5.isNullOrBlank()) drinksIngredients += "\n $strIngredient5"
            if (!strIngredient6.isNullOrBlank()) drinksIngredients += "\n $strIngredient6"
            if (!strIngredient7.isNullOrBlank()) drinksIngredients += "\n $strIngredient7"
            if (!strIngredient8.isNullOrBlank()) drinksIngredients += "\n $strIngredient8"
            if (!strIngredient9.isNullOrBlank()) drinksIngredients += "\n $strIngredient9"
            if (!strIngredient10.isNullOrBlank()) drinksIngredients += "\n $strIngredient10"
            if (!strIngredient11.isNullOrBlank()) drinksIngredients += "\n $strIngredient11"
            if (!strIngredient12.isNullOrBlank()) drinksIngredients += "\n $strIngredient12"
            return drinksIngredients
        }
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}