package com.example.lesson7.ui.fragments.hero_details

import HeroDetailsViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.lesson7.R
import com.example.lesson7.models.Hero

class HeroDetailsFragment(private val hero: Hero? = null) : Fragment() {
    private lateinit var viewModel: HeroDetailsViewModel
    private var imageView: ImageView? = null
    private var textView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.hero_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.hero_image)
        textView = view.findViewById(R.id.hero_full_info)

        viewModel = ViewModelProvider(this)[HeroDetailsViewModel::class.java]
        if (hero != null) setDetails(hero)
        viewModel.heroToUpdate.observe(viewLifecycleOwner) { onViewUpdate(it) }
    }

    private fun onViewUpdate(hero: Hero?) {
        imageView?.let { Glide.with(requireView()).load(hero?.images?.lg).into(it) }
        textView?.text = hero?.allInfo()
    }

    fun setDetails(hero: Hero) {
        viewModel.saveData(hero)
    }
}