package com.example.lesson7

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val sfm = supportFragmentManager
        val heroesFragment = sfm.findFragmentById(R.id.heroes) as? HeroesListFragment
        val heroFragment = sfm.findFragmentById(R.id.hero_info) as? HeroDetailsFragment
        val orientation = resources.configuration.orientation

        heroesFragment?.setOnItemClickedListener {
            if (heroFragment != null && orientation == Configuration.ORIENTATION_LANDSCAPE) {
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
