package com.boshra.githubrepo.model

import android.annotation.TargetApi
import android.os.Build
import com.boshra.githubrepo.dataModel.Details
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*


interface GitHubService {

    @GET("/repositories")
    suspend fun getAllRepos(
        @Query("since") page_index: Int
    ): ArrayList<Details>

    @GET("/repos/{part_1}/{part_2}")
    suspend fun getSingleRepo(
        @Path("part_1") part_1: String,
        @Path("part_2") part_2: String
    ): Details
}

@TargetApi(Build.VERSION_CODES.O)
fun createGitHubService(): GitHubService {
    val BASE_URL = "https://api.github.com"
    val authToken = "Basic " + Base64.getEncoder().encode("boshrahi:Android@7".toByteArray()).toString(Charsets.UTF_8)
    val httpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder()
                .header("Accept", "application/vnd.github.v3+json")
                .header("Authorization", authToken)
            val request = builder.build()
            chain.proceed(request)
        }
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()
    return retrofit.create(GitHubService::class.java)
}