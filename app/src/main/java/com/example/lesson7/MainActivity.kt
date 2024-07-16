package com.example.lesson7

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson7.models.Hero
import com.example.lesson7.network.HeroService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.Collections

class MainActivity : Activity() {
    private val disposable = CompositeDisposable()
    private var adapter: HeroesRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.heroes_layout)

        val recyclerView: RecyclerView = findViewById(R.id.hero_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        attachTouchHelper(recyclerView)

        val api = ApiClient.retrofit.create(HeroService::class.java)
        val result = api.getHeroes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onItemsFetched(it, recyclerView)
            }, {
                onItemsFetchedError(it)
            })

        disposable.add(result)
    }

    private fun attachTouchHelper(recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
            ): Int {
                return makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.END)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                val fromIndex = viewHolder.absoluteAdapterPosition
                val toIndex = target.absoluteAdapterPosition
                adapter?.items?.let { Collections.swap(it, fromIndex, toIndex) }
                adapter?.notifyItemMoved(fromIndex, toIndex)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (direction == ItemTouchHelper.END) {
                    val position = viewHolder.absoluteAdapterPosition
                    adapter?.items?.removeAt(position)
                    adapter?.notifyItemRemoved(position)
                }
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun onItemsFetched(heroes: List<Hero>, recyclerView: RecyclerView) {
        adapter = HeroesRecyclerViewAdapter(heroes as ArrayList<Hero>) { hero ->
            AlertDialog.Builder(this@MainActivity)
                .setTitle(hero.name)
                .setMessage(hero.allInfo())
                .create()
                .show()
        }
        recyclerView.adapter = adapter
    }

    private fun onItemsFetchedError(error: Throwable) {
        Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
