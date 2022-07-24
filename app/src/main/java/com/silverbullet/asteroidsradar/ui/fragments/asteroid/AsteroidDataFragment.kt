package com.silverbullet.asteroidsradar.ui.fragments.asteroid

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.silverbullet.asteroidsradar.R
import com.silverbullet.asteroidsradar.databinding.AsteroidDataFragmentBinding
import timber.log.Timber

class AsteroidDataFragment : Fragment() {

    private lateinit var binding: AsteroidDataFragmentBinding
    val args: AsteroidDataFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AsteroidDataFragmentBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val asteroid = args.asteroid
        binding.asteroid = asteroid
        binding.apply {
            executePendingBindings()
            helpIconButton?.setOnClickListener {
                showInfoDialogBox()
            }
        }
    }

    private fun showInfoDialogBox() {
        AlertDialog.Builder(activity)
            .setMessage(R.string.au_info)
            .setPositiveButton("Ok",null)
            .create()
            .show()
    }
}