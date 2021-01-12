package me.miguelos.sample.presentation.ui.beers

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import me.miguelos.base.ui.BaseFragment
import me.miguelos.base.ui.EndlessRecyclerViewScrollListener
import me.miguelos.base.util.ErrorMessageFactory
import me.miguelos.base.util.autoCleared
import me.miguelos.base.util.imageloader.ImageLoader
import me.miguelos.base.util.observe
import me.miguelos.base.util.showSnackbar
import me.miguelos.sample.R
import me.miguelos.sample.databinding.FragmentBeersBinding
import me.miguelos.sample.presentation.model.Beer
import me.miguelos.sample.presentation.ui.MainActivity
import me.miguelos.sample.presentation.ui.MainActivity.Companion.ARG_ID
import me.miguelos.sample.presentation.ui.beers.adapter.BeersAdapter
import java.net.UnknownHostException
import javax.inject.Inject


@AndroidEntryPoint
class BeersFragment : BaseFragment(), BeersAdapter.BeerItemListener {

    private var binding: FragmentBeersBinding by autoCleared()
    private val viewModel: BeersViewModel by viewModels()

    @Inject
    lateinit var imageLoader: ImageLoader


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBeersBinding.inflate(inflater, container, false)
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
        (requireActivity() as? MainActivity)?.updateTitle(getString(R.string.title_fragment_list))

        initAdapter()
        initSearch()
    }

    override fun onResume() {
        super.onResume()
        loadBeers()
    }

    private fun initAdapter() {
        var rvAdapter = getRVAdapter()
        if (rvAdapter == null) {
            rvAdapter = BeersAdapter(this, imageLoader)
            rvAdapter.let {
                binding.beersRv.run {
                    val linearLayoutManager = LinearLayoutManager(requireContext())
                    layoutManager = linearLayoutManager
                    adapter = it
                    addOnScrollListener(object :
                        EndlessRecyclerViewScrollListener(linearLayoutManager) {
                        override fun onLoadMore(
                            page: Int,
                            totalItemsCount: Int,
                            view: RecyclerView?
                        ) {
                            viewModel.loadBeers(totalItemsCount = totalItemsCount)
                        }
                    })
                }
            }
        }
    }

    private fun initSearch() {
        binding.searchEt.apply {
            setOnEditorActionListener { _, actionId, _ ->
                if (
                    actionId == EditorInfo.IME_ACTION_GO
                    || actionId == EditorInfo.IME_ACTION_SEARCH
                ) {
                    loadBeers()
                    true
                } else {
                    false
                }
            }
            setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN
                    && keyCode == KeyEvent.KEYCODE_ENTER
                ) {
                    loadBeers()
                    true
                } else {
                    false
                }
            }
            addTextChangedListener {
                doAfterTextChanged {
                    loadBeers()
                }
            }
        }
    }

    private fun loadBeers() {
        binding.beersRv.run runRV@{
            scrollToPosition(0)
            viewModel.loadBeers(getSearchQuery().toString())
            getRVAdapter()?.run runAdapter@{
                clear()
                this@runRV.post {
                    notifyDataSetChanged()
                }
            }
            recycledViewPool.clear()
        }
    }

    private fun getSearchQuery() =
        binding.searchEt.text.trim()

    private fun observeViewState() {
        observe(viewModel.viewState) { handleViewState(it) }
        observe(viewModel.errorState) { handleFeedbackState(it) }
        observe(viewModel.beersState) { handleBeersState(it) }
    }

    private fun handleBeersState(beers: List<Beer>) {
        getRVAdapter()?.run {
            addItems(ArrayList(beers))
            binding.emptyListTv.visibility =
                if (itemCount > 0) {
                    GONE
                } else {
                    VISIBLE
                }
        }
    }

    private fun handleFeedbackState(error: Throwable) {
        if (error is UnknownHostException) {
            (requireActivity() as? MainActivity)?.showDialog(
                getString(R.string.dialog_offline)
            )
        } else {
            binding.beersCl.showSnackbar(ErrorMessageFactory.create(requireContext(), error))
        }
    }

    private fun handleViewState(viewState: BeersViewState) {
        binding.listPb.visibility = if (viewState.isLoading) {
            VISIBLE
        } else {
            GONE
        }
    }

    private fun getRVAdapter() = (binding.beersRv.adapter as? BeersAdapter)

    override fun onClickedBeer(beer: Beer) {
        findNavController().navigate(
            R.id.action_beersFragment_to_beerDetailFragment,
            bundleOf(ARG_ID to beer.id)
        )
    }
}
