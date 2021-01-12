package me.miguelos.sample.presentation.ui.beer_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.miguelos.base.ui.BaseFragment
import me.miguelos.base.util.ErrorMessageFactory
import me.miguelos.base.util.autoCleared
import me.miguelos.base.util.imageloader.ImageLoader
import me.miguelos.base.util.observe
import me.miguelos.base.util.showSnackbar
import me.miguelos.sample.R
import me.miguelos.sample.databinding.FragmentBeerDetailBinding
import me.miguelos.sample.presentation.model.Beer
import me.miguelos.sample.presentation.ui.MainActivity
import me.miguelos.sample.presentation.ui.MainActivity.Companion.ARG_ID
import javax.inject.Inject


@AndroidEntryPoint
class BeerDetailFragment : BaseFragment() {

    private var binding: FragmentBeerDetailBinding by autoCleared()
    private val viewModel: BeerDetailViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeerDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    override fun onStart() {
        super.onStart()
        observeViewState()
    }

    private fun initUi() {
        (requireActivity() as? MainActivity)?.updateTitle(getString(R.string.title_fragment_detail))

        arguments?.getLong(ARG_ID)?.let { viewModel.saveId(it) }
    }

    private fun observeViewState() {
        observe(viewModel.beerState) { handleBeerState(it) }
        observe(viewModel.viewState) { handleViewState(it) }
        observe(viewModel.errorState) { handleFeedbackState(it) }
    }

    private fun handleBeerState(beer: Beer) {
        binding.detailNameTv.text = beer.name
        binding.detailDescriptionTv.text = beer.description
        beer.abv.let { binding.detailAbvTv.text = it.toString() }
        imageLoader.loadImage(binding.detailImageIv, beer.thumbnail, R.drawable.beer_placeholder)
    }

    private fun handleFeedbackState(error: Throwable) {
        binding.beerCl.showSnackbar(ErrorMessageFactory.create(requireContext(), error))
    }

    private fun handleViewState(viewState: BeerViewState) {
        binding.detailPb.visibility = if (viewState.isLoading) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}
