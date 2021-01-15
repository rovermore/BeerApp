package com.example.beerapp.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.beerapp.BeerApp
import com.example.beerapp.R
import com.example.beerapp.databinding.FragmentDetailBinding
import com.example.beerapp.model.canon.Beer
import com.example.beerapp.utils.ViewState
import com.example.beerapp.utils.gone
import com.example.beerapp.utils.visible
import com.squareup.picasso.Picasso
import javax.inject.Inject


class DetailFragment : Fragment() {

    @Inject
    lateinit var detailViewModel: DetailViewModel

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private var beerId: Int? = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val bundle = arguments
        beerId = bundle?.getInt("beerID")
        beerId?.let { detailViewModel.initialize(it) }
        setupObservers()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    init {
        BeerApp.daggerAppComponent().inject(this)
    }

    private fun setupReloadButton() {
        beerId?.let {
            binding.detailErrorView.reloadButton.setOnClickListener {
                detailViewModel.initialize(beerId!!)
            }
        }
    }

    private fun setupObservers() {
        observeState()
    }

    private fun observeState() {
        detailViewModel.viewState.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })
    }

    private fun setupUI() {
        setupReloadButton()
    }

    private fun setupView(beer: Beer) {
        binding.apply {
            beer.name?.let { titleTextView.text = beer.name }
            beer.date?.let { releaseDate.text = beer.date }
            beer.graduation?.let { rating.text = beer.graduation.toString() }
            beer.description?.let { overviewTextView.text = beer.description }
            beer.ibu?.let { bitterness.text = beer.ibu.toString() }
            beer.image?.let {
                Picasso.with(binding.root.context)
                    .load(beer.image)
                    .error(R.drawable.ic_baseline_error_24)
                    .into(posterImageView)
            }
        }
    }

    private fun updateUI(it: ViewState<Beer>?) {
        when (it) {
            ViewState.Error -> setErrorView()
            ViewState.Loading -> setLoadingView()
            is ViewState.Content -> {
                setupView(it.data)
                setSuccessView()
            }
        }
    }

    private fun setErrorView() {
        binding.apply {
            detailProgressBar.gone()
            detailContent.gone()
            detailErrorView.root.visible()
        }
    }

    private fun setLoadingView() {
        binding.apply {
            detailErrorView.root.gone()
            detailContent.gone()
            detailProgressBar.visible()
        }
    }

    private fun setSuccessView() {
        binding.apply {
            detailProgressBar.gone()
            detailErrorView.root.gone()
            detailContent.visible()
        }
    }
}