package com.example.lesson7

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.lesson7.models.Hero

class HeroDetailsFragment : Fragment() {
    private var imageView: ImageView? = null
    private var textView: TextView? = null
    private var hero: Hero? = null
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
        show()
    }

    fun setDetails(hero: Hero) {
        this.hero = hero
    }

    fun show() {
        imageView?.let { Glide.with(requireView()).load(hero?.images?.lg).into(it) }
        textView?.text = hero?.allInfo()
    }
}