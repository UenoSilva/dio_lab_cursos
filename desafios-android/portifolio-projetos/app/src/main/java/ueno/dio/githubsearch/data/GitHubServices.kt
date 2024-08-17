package ueno.dio.githubsearch.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ueno.dio.githubsearch.domain.Repository

interface GitHubServices {
    @GET("users/{user}/repos")
    fun getAllRepositoriesByUser(@Path("user") user: String): Call<List<Repository>>
}