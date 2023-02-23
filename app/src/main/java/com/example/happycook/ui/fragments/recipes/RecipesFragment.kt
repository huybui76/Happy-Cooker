package com.example.happycook.ui.fragments.recipes

import android.net.Network
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.happycook.MainViewModel
import com.example.happycook.R
import com.example.happycook.adapter.RecipesAdapter
import com.example.happycook.util.Constants.Companion.API_KEY
import com.example.happycook.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*


@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mView: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mView=  inflater.inflate(R.layout.fragment_recipes, container, false)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
       // recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
        setupRecyclerView()
        requestApiData()
        return mView

}
    private fun requestApiData(){
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it)}
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun applyQueries():HashMap<String, String>{
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = " 50"
        queries["apiKey"]   = API_KEY
        queries["type"] = "snack"
        queries["diet"] =  "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"
        return queries
    }

    private fun setupRecyclerView(){
        mView.shimmer_recycler_view.adapter = mAdapter
        mView.shimmer_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect(){
        mView.shimmer_recycler_view.showShimmer()
    }
    private fun hideShimmerEffect(){
        mView.shimmer_recycler_view.hideShimmer()
    }

}