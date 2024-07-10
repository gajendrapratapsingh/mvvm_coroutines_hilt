package com.info.testtuts.network

import com.info.testtuts.models.Post
import retrofit2.http.GET

interface PostApiService {

    @GET("/posts")
    suspend fun getPosts(): List<Post>
}
