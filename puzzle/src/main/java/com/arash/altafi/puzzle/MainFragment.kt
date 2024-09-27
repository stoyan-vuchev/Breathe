package com.arash.altafi.puzzle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.arash.altafi.puzzle.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val binding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        init()
        return binding.root
    }

    private fun init() = binding.apply {
        btnThree.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToPuzzleFragment(3)
            )
        }
        btnFour.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToPuzzleFragment(4)
            )
        }
        btnFive.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToPuzzleFragment(5)
            )
        }
    }
}
