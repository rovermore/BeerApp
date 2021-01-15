package com.example.beerapp.screen.main

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beerapp.BeerApp
import com.example.beerapp.R
import com.example.beerapp.databinding.FragmentMainBinding
import com.example.beerapp.model.canon.Beer
import com.example.beerapp.utils.ViewState
import com.example.beerapp.utils.gone
import com.example.beerapp.utils.visible
import javax.inject.Inject


class MainFragment : Fragment(), MainAdapter.OnItemClicked{

    @Inject
    lateinit var mainViewModel: MainViewModel

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var mainAdapter = MainAdapter(mutableListOf<Beer>(), this)
    private lateinit var mLayoutManager: GridLayoutManager

    private var page = 1
    private var mQuery = "mahou"
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        mLayoutManager = GridLayoutManager(requireContext(), 2)
        setupObservers()
        return binding.root
    }

    init { BeerApp.daggerAppComponent().inject(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupReloadButton() {
        binding.errorView.reloadButton.setOnClickListener {
            mainAdapter.clearMainAdapter()
            mainViewModel.loadData(mQuery, page)
        }
    }

    private fun setupObservers() {
        observeState()
    }

    private fun observeState() {
        mainViewModel.viewState.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })
    }

    private fun setupUI() {
        setupReloadButton()
        setSearchView()
        setUpRecyclerView()
    }

    private fun setSearchView() {
        binding.searchBar.apply {
            queryHint = resources.getString(R.string.search_hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        mQuery = query
                        mainViewModel.loadData(query, page)
                    }
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    query?.let {
                        mQuery = query
                        page = 1
                        mainAdapter.clearMainAdapter()
                        mainViewModel.loadData(query, page)
                    }
                    return false
                }
            })

        }
    }

    private fun updateRecyclerView(list: List<Beer>) {
        mainAdapter.updateBeerList(list.toMutableList())
    }

    private fun setUpRecyclerView() {
        binding.mainRecycler.apply {
            layoutManager = mLayoutManager
            setHasFixedSize(true)
            adapter = mainAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) {
                        setUpScrollLoader(mLayoutManager)
                    }
                    super.onScrolled(recyclerView, dx, dy)
                }
            })
        }
    }

    private fun setUpScrollLoader(layoutManager: GridLayoutManager) {
        val visibleItemCount = layoutManager.childCount
        val scrolledItems = layoutManager.findFirstCompletelyVisibleItemPosition()
        val totalCount = layoutManager.itemCount

        if (!isLoading) {
            if ((visibleItemCount + scrolledItems) >= totalCount) {
                page += 1
                mainViewModel.loadData(mQuery, page)
            }
        }
    }

    private fun updateUI(viewState: ViewState<List<Beer>>) {
        when (viewState) {
            ViewState.Error -> setErrorView()
            ViewState.Loading -> setLoadingView()
            ViewState.FinishLoading -> finishLoading()
            is ViewState.Content -> {
                updateRecyclerView(viewState.data)
                setSuccessView()
            }
        }
    }

    private fun setErrorView() {
        binding.apply {
            mainRecycler.gone()
            progressBar.gone()
            errorView.root.visible()
        }
        isLoading = false
    }

    private fun setLoadingView() {
        binding.apply {
            errorView.root.gone()
            //mainRecycler.gone()
            progressBar.visible()
        }
        isLoading = true
    }

    private fun finishLoading() {
        binding.progressBar.gone()
    }

    private fun setSuccessView() {
        binding.apply {
            progressBar.gone()
            errorView.root.gone()
            mainRecycler.visible()
        }
        isLoading = false
    }

    override fun itemClicked(beer: Beer) {
        beer.id?.let { navigateToDetailFragment(it) }
    }

    private fun navigateToDetailFragment(id: Int) {
        val bundle = bundleOf("beerID" to id)
        NavHostFragment.findNavController(this).navigate(R.id.action_MainFragment_to_DetailFragment, bundle)
    }

}