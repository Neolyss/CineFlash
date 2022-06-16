package com.example.test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.adapters.HistoryAdapter
import com.example.test.adapters.TrendAdapter
import com.example.test.data.history.History
import com.example.test.data.trailer.YoutubeTrailer
import com.example.test.databinding.FragmentHistoryBinding
import com.google.gson.Gson
import java.io.IOException

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)

        lateinit var jsonString: String
        val assets = this.context?.assets!!
        try {
            jsonString = assets.open("json/history.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

        val gson : Gson = Gson()
        val data: History = gson.fromJson(jsonString, History::class.java)

        binding.history.layoutManager = LinearLayoutManager(context)
        binding.history.adapter = HistoryAdapter(data, this)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.buttonSecond.setOnClickListener {
            //findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        //}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}