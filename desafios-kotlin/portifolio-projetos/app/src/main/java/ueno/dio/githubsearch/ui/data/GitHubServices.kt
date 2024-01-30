package ueno.dio.githubsearch.ui.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubServices {

    @GET("users/{user}/repor")
    fun getAllRepositoriesByUser(@Path("user") user: String): Call<List<Repository>>

}