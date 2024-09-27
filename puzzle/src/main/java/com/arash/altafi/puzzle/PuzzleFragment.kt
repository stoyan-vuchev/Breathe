package com.arash.altafi.puzzle

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.arash.altafi.puzzle.databinding.FragmentPuzzleBinding
import com.arash.altafi.puzzle.utils.PuzzleBoardView

class PuzzleFragment : Fragment() {

    private val binding by lazy {
        FragmentPuzzleBinding.inflate(layoutInflater)
    }

    private val args by navArgs<PuzzleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        init()
        return binding.root
    }

    private fun init() = binding.apply {
        val puzzleBoardView = PuzzleBoardView(requireContext(), args.number) { isSolve ->
            if (isSolve) {
                val alertDialog = AlertDialog.Builder(context).create()
                alertDialog.setTitle("Well done!")
                alertDialog.setCancelable(false)
                alertDialog.setMessage("Do you want to play again?")
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes") { dialog, _ ->
                    btnNewGame.performClick()
                    dialog.dismiss()
                }
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { dialog, _ ->
                    findNavController().navigateUp()
                    dialog.dismiss()
                }
                alertDialog.show()
            }
        }
        puzzleContainer.addView(puzzleBoardView)

        btnNewGame.setOnClickListener {
            puzzleBoardView.initGame()
            puzzleBoardView.invalidate()
        }
    }
}
