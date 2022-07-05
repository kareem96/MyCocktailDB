package com.kareemdev.mycocktailsdb.presentation.ui.fragment.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kareemdev.mycocktailsdb.R
import com.kareemdev.mycocktailsdb.databinding.FragmentSearchBinding
import com.kareemdev.mycocktailsdb.presentation.adpater.CocktailAdapter
import com.kareemdev.mycocktailsdb.utils.ConnectionLiveData
import com.kareemdev.mycocktailsdb.utils.Constants.Companion.SEARCH_COCKTAIL_TIME_DELAY
import com.kareemdev.mycocktailsdb.utils.Constants.Companion.searchName
import com.kareemdev.mycocktailsdb.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class SearchFragment : Fragment() {
    val TAG = "Search"
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var adapterCocktail: CocktailAdapter
    private val viewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        connectionLiveData = ConnectionLiveData(this.requireContext())
        connectionLiveData.observe(viewLifecycleOwner){ isAvailable ->
            if(isAvailable){
                setupObserver()
                var job: Job? = null
                binding.etSearch.addTextChangedListener { editable ->
                    job?.cancel()
                    job = MainScope().launch {
                        delay(SEARCH_COCKTAIL_TIME_DELAY)
                        editable?.let {
                            if(editable.toString().isNotEmpty()){
                                viewModel.searchCocktail(editable.toString(), searchName)
                            }
                        }
                    }
                }
            }else{
                Toast.makeText(
                    this.requireContext(),
                    "No Internet Connection Available!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        adapterCocktail.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("drink", it)
            }
            findNavController().navigate(R.id.action_searchFragment2_to_detailFragment, bundle)
        }
    }

    private fun setupObserver() {
        viewModel.searchCocktail.observe(viewLifecycleOwner){ response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        adapterCocktail.diff.submitList(it.drinks)
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

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }
    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        adapterCocktail = CocktailAdapter()
        binding.rvSearchDrink.apply {
            layoutManager = GridLayoutManager(this@SearchFragment.requireContext(), 2)
            adapter = adapterCocktail
            hasFixedSize()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}