package com.example.lesson7

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson7.models.Hero
import com.example.lesson7.network.HeroService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MainActivity : Activity() {
    private val disposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.heroes_layout)

        val recyclerView: RecyclerView = findViewById(R.id.hero_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val api = ApiClient.retrofit.create(HeroService::class.java)

        val result = api.getHeroes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onItemFetched(it, recyclerView)
            }, {
                onItemFetchedError(it)
            })

        disposable.add(result)
    }

    private fun onItemFetched(heroes: List<Hero>, recyclerView: RecyclerView) {
        val myAdapter = HeroesRecyclerViewAdapter(heroes as MutableList<Hero>) { hero ->
            AlertDialog.Builder(this@MainActivity)
                .setTitle(hero.name)
                .setMessage(hero.allInfo())
                .create()
                .show()
        }
        recyclerView.adapter = myAdapter
    }

    private fun onItemFetchedError(error: Throwable) {
        Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
