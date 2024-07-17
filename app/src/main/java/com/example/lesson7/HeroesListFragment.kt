package com.example.lesson7

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson7.models.Hero
import com.example.lesson7.network.HeroService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HeroesListFragment : Fragment() {
    private val disposable = CompositeDisposable()
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

        val recyclerView: RecyclerView = view.findViewById(R.id.hero_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(view.context)

        val api = ApiClient.retrofit.create(HeroService::class.java)
        val result = api.getHeroes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onItemsFetched(it, recyclerView)
            }, {
                onItemsFetchedError(it, view.context)
            })

        disposable.add(result)
    }

    private fun onItemsFetched(heroes: List<Hero>, recyclerView: RecyclerView) {
        adapter = HeroesRecyclerViewAdapter(heroes as ArrayList<Hero>) {
            onItemClick(it)
        }
        recyclerView.adapter = adapter
    }

    private fun onItemsFetchedError(error: Throwable, context: Context) {
        Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }

    fun setOnItemClickedListener(onItemClick: (result: Hero) -> Unit) {
        this.onItemClick = onItemClick
    }
}
