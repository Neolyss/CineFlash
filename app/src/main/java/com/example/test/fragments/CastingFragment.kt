package com.example.test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R
import com.example.test.adapters.ActorAdapter
import com.example.test.data.movie.Actor
import com.example.test.databinding.FragmentCastingBinding


class CastingFragment : Fragment() {

    companion object {
        private const val TAG = "Casting"
    }

    private var _binding: FragmentCastingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCastingBinding.inflate(inflater, container, false)

        val fragmentCasting = this
        val actors = arguments!!.get("actors") as List<*>

        binding.actors.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.actors.adapter = ActorAdapter(actors.filterIsInstance<Actor>(), fragmentCasting)

        return binding.root
    }
}