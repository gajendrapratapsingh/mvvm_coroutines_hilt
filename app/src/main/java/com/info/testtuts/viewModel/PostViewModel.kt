package com.info.testtuts.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.info.testtuts.models.Post
import com.info.testtuts.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class PostViewState {
    object Loading : PostViewState()
    data class Success(val posts: List<Post>) : PostViewState()
    data class Error(val message: String) : PostViewState()
}

@HiltViewModel
class PostViewModel @Inject constructor(private val repository: PostRepository) : ViewModel() {

    private val _posts = MutableLiveData<PostViewState>()
    val posts: LiveData<PostViewState>
        get() = _posts

    init {
        fetchPosts()
    }

    private fun fetchPosts() {
        viewModelScope.launch {
            _posts.value = PostViewState.Loading
            try {
                val fetchedPosts = repository.getPosts()
                _posts.value = PostViewState.Success(fetchedPosts)
            } catch (e: Exception) {
                _posts.value = PostViewState.Error(e.message ?: "Unknown error")
            }
        }
    }
}


