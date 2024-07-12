package com.info.testtuts.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.info.testtuts.R
import com.info.testtuts.adapters.PostAdapter
import com.info.testtuts.databinding.FragmentHomeBinding
import com.info.testtuts.utils.AppUtils
import com.info.testtuts.utils.ConnectivityHelper
import com.info.testtuts.viewModel.PostViewModel
import com.info.testtuts.viewModel.PostViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Home : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: PostViewModel by viewModels()
    private lateinit var postAdapter: PostAdapter
    private lateinit var appUtils: AppUtils

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        appUtils = AppUtils.getInstance(requireActivity().window.decorView.rootView)

        if (ConnectivityHelper.isConnectedToInternet(requireContext())) {
            viewModel.posts.observe(viewLifecycleOwner) { state ->
                when (state) {
                    is PostViewState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    }
                    is PostViewState.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        postAdapter.setPosts(state.posts)
                    }
                    is PostViewState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.recyclerView.visibility = View.GONE
                        Toast.makeText(context, "Error: ${state.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            val rootView = view.findViewById<View>(android.R.id.content)
            appUtils.showSnackbar(getString(R.string.connection), Snackbar.LENGTH_LONG, rootView)
        }
    }

    private fun setupRecyclerView() {
        postAdapter = PostAdapter()
        binding.recyclerView.apply {
            adapter = postAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}
