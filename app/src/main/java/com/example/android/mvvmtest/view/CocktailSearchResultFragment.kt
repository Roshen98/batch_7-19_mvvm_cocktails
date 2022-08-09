package com.example.android.mvvmtest.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.mvvmtest.R
import com.example.android.mvvmtest.databinding.FragmentCocktailResultBinding
import com.example.android.mvvmtest.model.CocktailSearch
import com.example.android.mvvmtest.view.adapter.CocktailAdapter
import com.example.android.mvvmtest.viewmodel.CocktailViewModel


private const val TAG = "CocktailSearchResultFra"


class CocktailSearchResultFragment: Fragment(){

    private lateinit var binding: FragmentCocktailResultBinding

    /**
     *  intention of viewModel to separated from the lifecycle.
     *  viewModel is a container that need to survive when lifecycle create and destroy.
     * */
    private val viewModel: CocktailViewModel by lazy {
        ViewModelProvider(this)[CocktailViewModel::class.java]
    }

    private lateinit var adapter: CocktailAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentCocktailResultBinding.inflate(
            inflater,
            container,
            false
        )

        initObservables()
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.cocktailSearch.setOnQueryTextListener(
            object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return query?.let {
                        viewModel.searchCocktail(it)

                         true
                    } ?: false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            }
        )
        adapter = CocktailAdapter {
            // Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            requireActivity().displayCocktailDetails(it)
        }
        binding.searchList.adapter = adapter
        binding.searchList.layoutManager = LinearLayoutManager(context)
    }

    /**
    * LiveData follows the observer pattern. LiveData notifies Observer objects when underlying data changes.
     * You can consolidate your code to update the UI in these Observer objects.
     * That way, you don't need to update the UI every time the app data changes because the observer does it for you.
    * */
    private fun initObservables() {

        viewModel.cocktailSearchResult.observe(viewLifecycleOwner, Observer {
            updateAdapter(it)
        })
    }

    private fun updateAdapter(dataSet: CocktailSearch) {
        // Log.d(TAG, "updateAdapter:  $dataSet")
        adapter.submitList(dataSet.drinks)
    }

    private fun FragmentActivity.displayCocktailDetails(idDrink: String){
        supportFragmentManager.beginTransaction()
            .replace(R.id.cocktail_container,CocktailDetailsFragment.newInstance(idDrink))
            .addToBackStack(null)
            .commit()
    }

}