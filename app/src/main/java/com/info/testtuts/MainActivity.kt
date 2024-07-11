package com.info.testtuts

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
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

        setupNavigationDrawer()

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

    private fun setupNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_item1 -> {
                    // Handle navigation item 1 click
                    true
                }
                R.id.nav_item2 -> {
                    // Handle navigation item 2 click
                    true
                }
                R.id.nav_item3 -> {
                    // Handle navigation item 3 click
                    true
                }
                else -> false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (binding.drawerLayout.isDrawerOpen(binding.navigationView)) {
                binding.drawerLayout.closeDrawer(binding.navigationView)
            } else {
                binding.drawerLayout.openDrawer(binding.navigationView)
            }
            return true
        }
        return super.onOptionsItemSelected(item)
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