package com.example.lesson7.ui.fragments.hero_list

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.lesson7.R
import com.example.lesson7.models.Hero
import com.example.lesson7.ui.fragments.hero_details.HeroDetailsFragment

class HeroesListFragment : Fragment() {
    private lateinit var viewModel: HeroesListViewModel
    private var adapter: HeroesRecyclerViewAdapter? = null
    private var onItemClick: (result: Hero) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        viewGroup: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.heroes_list, viewGroup, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parentFragmentManager.popBackStack()

        onItemClick = { hero -> setOnItemClickedListener(hero) }

        val recyclerView: RecyclerView = view.findViewById(R.id.hero_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        val swipeContainer: SwipeRefreshLayout = view.findViewById(R.id.refreshLayout)
        val progressBar: ProgressBar = view.findViewById(R.id.list_loading)

        viewModel = ViewModelProvider(this)[HeroesListViewModel::class.java]
        viewModel.getHeroes()
        viewModel.uiHeroesState.observe(viewLifecycleOwner) { uiState ->
            swipeContainer.isRefreshing = false

            when (uiState) {
                is HeroesListViewModel.UIHeroesState.Error -> {
                    onItemsFetchedError(uiState.error, view.context)
                }

                is HeroesListViewModel.UIHeroesState.Result -> {
                    onItemsFetched(uiState.heroes, recyclerView)
                }

                is HeroesListViewModel.UIHeroesState.Empty -> Unit
                is HeroesListViewModel.UIHeroesState.Processing -> Unit
            }
            progressBar.isVisible = uiState == HeroesListViewModel.UIHeroesState.Processing
        }

        swipeContainer.setOnRefreshListener { viewModel.getHeroes() }
    }

    private fun onItemsFetched(heroes: List<Hero>, recyclerView: RecyclerView) {
        adapter = HeroesRecyclerViewAdapter(heroes as ArrayList<Hero>) {
            onItemClick(it)
        }
        recyclerView.adapter = adapter
    }

    private fun onItemsFetchedError(error: String, context: Context) {
        Toast.makeText(context, "Error: $error", Toast.LENGTH_LONG).show()
    }

    private fun setOnItemClickedListener(hero: Hero) {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val heroFragment = parentFragmentManager.findFragmentById(R.id.hero_info) as HeroDetailsFragment
            heroFragment.setDetails(hero)
        } else {
            val heroFragmentToAdd = HeroDetailsFragment(hero)
            parentFragmentManager.beginTransaction()
                .add(R.id.heroes, heroFragmentToAdd)
                .addToBackStack("HeroDetailsFragment")
                .commit()
        }
    }
}
