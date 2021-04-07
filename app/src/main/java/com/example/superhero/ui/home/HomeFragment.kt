package com.example.superhero.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.superhero.R
import com.example.superhero.base.BaseFragment
import com.example.superhero.databinding.HomeScreenBinding
import com.example.superhero.ui.cardDetails.CardDetailsFragmentDirections
import com.example.superhero.utils.LoadMoreListener
import com.example.superhero.utils.SuperheroCardDecorator



class HomeFragment : BaseFragment() {

    private var binding: HomeScreenBinding? = null

    private val viewModel by viewModels<HomeViewModel>()

    override fun getViewModelInstance() = viewModel

    /**პასუხობს კლიკებს, გადადის ქარდის დეტალურ ვიუზე**/
    private val adapter = CardAdapter{
        val action = CardDetailsFragmentDirections.actionGlobalCardDetailsFragment(it)
        activity?.findNavController(R.id.mainContainer)?.navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, 2)
        layoutManager.spanSizeLookup = CardAdapter.LoaderSpanSizeLookup(adapter)
        binding?.apply {
            recycleView.layoutManager = layoutManager
            recycleView.adapter = adapter
            recycleView.addItemDecoration(
                SuperheroCardDecorator(
                    // ქარდების მდგომარეობა ჰოუმ სქრინზე
                    itemHorizontalInsets = resources.getDimensionPixelSize(R.dimen.card_list_hor_inst),
                    itemHorizontalSpacing = resources.getDimensionPixelSize(R.dimen.card_list_hor_space),
                    itemVerticalInsets = resources.getDimensionPixelSize(R.dimen.card_list_ver_inst),
                    itemVerticalSpacing = resources.getDimensionPixelSize(R.dimen.card_list_vrt_space),
                )
            )
            recycleView.addOnScrollListener(LoadMoreListener() {
                viewModel.onScrollEndReached()
            })
            swipeToRefresh.setOnRefreshListener {
                viewModel.onRefresh()
            }
            viewModel.items.observe(viewLifecycleOwner) {
                adapter.cardList = it
            }
            viewModel.loadingMore.observe(viewLifecycleOwner) {
                adapter.loadingMore = it
                if (swipeToRefresh.isRefreshing && it) swipeToRefresh.isRefreshing = false
            }

        }
    }


}