package dev.keikem.catzapp.screens.fragment.dog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import coil.load
import com.google.android.material.progressindicator.CircularProgressIndicator
import dagger.hilt.android.AndroidEntryPoint
import dev.keikem.catzapp.R

@AndroidEntryPoint
class DogFragment : Fragment(R.layout.fragment_cat), LifecycleEventObserver {

    private var viewModel: DogViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = view.findViewById<ImageView>(R.id.image)
        val button = view.findViewById<Button>(R.id.button)
        val buttonLoad = view.findViewById<Button>(R.id.buttonLoad)
        val progress = view.findViewById<CircularProgressIndicator>(R.id.progress)

        viewModel?.imageUrl?.observe(viewLifecycleOwner) { url ->
            if (url.isNotEmpty()) {
                image.load(url)
                progress.isVisible = false
            }
        }

        buttonLoad.text = "Load Dog"
        button.text = "Navigate Back"

        buttonLoad.setOnClickListener {
            progress.isVisible = true
            viewModel?.loadFromRemote()
        }

        button.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.appNavHostFragment).popBackStack()
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                viewModel = ViewModelProvider(this)[DogViewModel::class.java]
            }

            else -> Unit
        }
    }
}