package com.info.testtuts

import android.os.Bundle
import android.view.MenuItem
import android.view.View

import android.widget.Toast

import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.ui.setupWithNavController

import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar

import com.info.testtuts.adapters.PostAdapter
import com.info.testtuts.databinding.ActivityMainBinding
import com.info.testtuts.utils.AppUtils
import com.info.testtuts.utils.ConnectivityHelper
import com.info.testtuts.viewModel.PostViewModel
import com.info.testtuts.viewModel.PostViewState
import dagger.hilt.android.AndroidEntryPoint


//https://fakestoreapi.com/products

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    //private val viewModel: PostViewModel by viewModels()
    //private lateinit var postAdapter: PostAdapter

    //private lateinit var appUtils: AppUtils

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationDrawer()

        // Initialize NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //appUtils = AppUtils.getInstance(window.decorView.rootView)

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    //Toast.makeText(this, navController.currentDestination?.label.toString(), Toast.LENGTH_LONG).show()
                    navController.navigate(R.id.homeFragment)
//                    if(navController.currentDestination?.label.toString() == "fragment_notifications") {
//                        navController.navigate(R.id.action_notificationsFragment_to_homeFragment)
//                    }
//                    else{
//                        navController.navigate(R.id.action_settingsFragment_to_homeFragment)
//                    }
                    true
                }
                R.id.navigation_notifications -> {
                      navController.navigate(R.id.notificationsFragment)
//                    if(navController.currentDestination?.label.toString() == "fragment_home") {
//                        navController.navigate(R.id.action_homeFragment_to_notificationsFragment)
//                    }
//                    else{
//                        navController.navigate(R.id.action_settingsFragment_to_notificationsFragment)
//                    }
                    true
                }
                R.id.navigation_settings -> {
                    navController.navigate(R.id.settingsFragment)
//                    if(navController.currentDestination?.label.toString() == "fragment_home") {
//                        navController.navigate(R.id.action_homeFragment_to_settingsFragment)
//                    }
//                    else{
//                        navController.navigate(R.id.action_notificationsFragment_to_settingsFragment)
//                    }
                    true
                }
                else -> false
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
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
                R.id.home -> {
                    binding.drawerLayout.closeDrawer(binding.navigationView)
                    true
                }
                R.id.order -> {
                    binding.drawerLayout.closeDrawer(binding.navigationView)
                    true
                }
                R.id.policy -> {
                    binding.drawerLayout.closeDrawer(binding.navigationView)
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


    override fun onBackPressed() {
        // Check if the current destination is a fragment that handles onBackPressed()
        val currentDestination = navController.currentDestination
        val handled = currentDestination?.id == R.id.settingsFragment || currentDestination?.id == R.id.notificationsFragment
        if (handled) {
            // Handle onBackPressed() in fragments as necessary
            // For example, you might want to pop the back stack or navigate up
            navController.popBackStack()
        } else {
            // If the current destination does not handle onBackPressed(), invoke default behavior
            super.onBackPressed()
        }
    }
}