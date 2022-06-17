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
import com.example.test.adapters.CrewAdapter
import com.example.test.data.movie.Actor
import com.example.test.data.movie.Director
import com.example.test.data.movie.Writer
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
        val directors = arguments!!.get("directors") as List<*>
        val writers = arguments!!.get("writers") as List<*>

        binding.actors.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.actors.adapter = ActorAdapter(actors.filterIsInstance<Actor>(), fragmentCasting)

        binding.crew.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.crew.adapter = CrewAdapter(directors.filterIsInstance<Director>(), writers.filterIsInstance<Writer>(), fragmentCasting)

        return binding.root
    }
}