package com.example.lesson7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val sfm = supportFragmentManager
        val heroesFragment = sfm.findFragmentById(R.id.heroes) as? HeroesListFragment
        val heroFragment = sfm.findFragmentById(R.id.hero_info) as? HeroDetailsFragment

        heroesFragment?.setOnItemClickedListener {
            if (heroFragment != null) {
                heroFragment.setDetails(it)
                heroFragment.show()
            } else {
                val heroFragmentToAdd = HeroDetailsFragment()
                heroFragmentToAdd.setDetails(it)

                sfm.beginTransaction()
                    .add(R.id.heroes, heroFragmentToAdd)
                    .addToBackStack("HeroDetailsFragment")
                    .commit()
            }
        }
    }
}
