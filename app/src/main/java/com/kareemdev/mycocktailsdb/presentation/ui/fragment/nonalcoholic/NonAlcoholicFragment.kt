package com.kareemdev.mycocktailsdb.presentation.ui.fragment.nonalcoholic

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kareemdev.mycocktailsdb.R
import com.kareemdev.mycocktailsdb.databinding.FragmentNonAlcoholicBinding
import com.kareemdev.mycocktailsdb.presentation.adpater.CocktailAdapter
import com.kareemdev.mycocktailsdb.utils.ConnectionLiveData
import com.kareemdev.mycocktailsdb.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlin.coroutines.coroutineContext


/**
 * A simple [Fragment] subclass.
 * Use the [NonAlcoholicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class NonAlcoholicFragment : Fragment() {
    private val TAG = "NonAlcoholic"
    private var _binding: FragmentNonAlcoholicBinding? = null
    private val binding: FragmentNonAlcoholicBinding get() = _binding!!

    private lateinit var connectionLiveData: ConnectionLiveData
    private val viewModel: NonAlcoholicViewModel by viewModels()
    private lateinit var adapterCocktail: CocktailAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = FragmentNonAlcoholicBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        connectionLiveData = ConnectionLiveData(this.requireContext())
        connectionLiveData.observe(viewLifecycleOwner) { isAvailable ->
            if (isAvailable) {
                viewModel.getNonAlcoholic()

                setupObserver()
            } else {
                Toast.makeText(
                    this.requireContext(),
                    "No Internet Connection Available!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        setupRecyclerView()
        setupOnClick()
    }

    private fun setupOnClick() {
        adapterCocktail.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("drink", it)
            }
            findNavController().navigate(
                R.id.action_homeCocktail_to_detailFragment,
                bundle
            )
        }
    }

    private fun setupRecyclerView() {
        adapterCocktail = CocktailAdapter()
        binding.rvCocktailList.apply {
            adapter = adapterCocktail
            layoutManager = GridLayoutManager(this@NonAlcoholicFragment.requireContext(), 2)
            hasFixedSize()
        }
    }

    private fun setupObserver() {
        viewModel.cocktailNonAlcoholic.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { list ->
                        adapterCocktail.diff.submitList(list.drinks)
                        hideProgressBar()
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "An Error Occured $message")
                        hideProgressBar()
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
}