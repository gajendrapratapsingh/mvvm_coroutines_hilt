package com.info.testtuts

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.info.testtuts.adapters.PostAdapter
import com.info.testtuts.databinding.ActivityMainBinding
import com.info.testtuts.viewModel.PostViewModel
import com.info.testtuts.viewModel.PostViewState
import dagger.hilt.android.AndroidEntryPoint
import org.w3c.dom.Text

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter

    private lateinit var postText : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        viewModel.posts.observe(this) { state ->
            when(state) {
                is PostViewState.Loading -> {
                    // Show progress bar or loading indicator
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is PostViewState.Success -> {
                    // Hide progress bar
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    // Update UI with fetched posts
                    //postText = state.posts.joinToString("\n") { "Title: ${it.title}, Body: ${it.body}" }
                    postAdapter.setPosts(state.posts)
                }

                is PostViewState.Error -> {
                    // Hide progress bar
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    // Display error message or handle error state
                    Toast.makeText(this@MainActivity, "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter()
        binding.recyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            // Optionally, add item decorations, animations, etc.
        }
    }
}