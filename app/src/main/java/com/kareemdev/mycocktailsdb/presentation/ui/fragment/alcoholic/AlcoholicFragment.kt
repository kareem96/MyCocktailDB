package com.kareemdev.mycocktailsdb.presentation.ui.fragment.alcoholic

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
import com.kareemdev.mycocktailsdb.databinding.FragmentAlcoholicBinding
import com.kareemdev.mycocktailsdb.presentation.adpater.CocktailAdapter
import com.kareemdev.mycocktailsdb.utils.ConnectionLiveData
import com.kareemdev.mycocktailsdb.utils.Resource
import dagger.hilt.android.AndroidEntryPoint


/**
 * A simple [Fragment] subclass.
 * Use the [AlcoholicFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class AlcoholicFragment : Fragment() {
    private var _binding: FragmentAlcoholicBinding? = null
    private val binding: FragmentAlcoholicBinding get() = _binding!!
    private val viewModel: AlcoholicViewModel by viewModels()
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var adapterCocktail: CocktailAdapter

    val TAG = "CocktailFragment"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        _binding = FragmentAlcoholicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectionLiveData = ConnectionLiveData(this.requireContext())
        connectionLiveData.observe(viewLifecycleOwner) { isAvailable ->
            if(isAvailable){
                viewModel.getAlcoholic()
                setupObserver()
            }else{
                Toast.makeText(
                    requireContext(),
                    "No Internet Connection Available!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        setupRecyclerView()

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
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@AlcoholicFragment.requireContext(), 2)
        }
    }


    private fun setupObserver() {
        viewModel.cocktailAlcoholic.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    response.data?.let { coctail ->
                        adapterCocktail.diff.submitList(coctail.drinks)
                        hideProgressBar()
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(TAG, "setupObserver: $message", )
                        hideProgressBar()
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}