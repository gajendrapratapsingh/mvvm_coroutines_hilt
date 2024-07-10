package com.info.testtuts.repository

import com.info.testtuts.models.Post
import com.info.testtuts.network.PostApiService
import javax.inject.Inject

class PostRepository @Inject constructor(private val apiService: PostApiService) {

    suspend fun getPosts(): List<Post> {
        return apiService.getPosts()
    }
}
