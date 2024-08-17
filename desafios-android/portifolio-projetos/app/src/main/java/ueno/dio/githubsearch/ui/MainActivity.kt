package ueno.dio.githubsearch.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ueno.dio.githubsearch.R
import ueno.dio.githubsearch.data.GitHubServices
import ueno.dio.githubsearch.domain.Repository
import ueno.dio.githubsearch.ui.adapter.RepositoryAdapter

class MainActivity : AppCompatActivity() {

    lateinit var userName: EditText
    lateinit var btnComfirmar: Button
    lateinit var repositories: RecyclerView
    lateinit var githubApi: GitHubServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
        setupRetrofit()
        showUserName()
    }

    fun setupView() {
        userName = findViewById(R.id.et_name_user)
        btnComfirmar = findViewById(R.id.bt_search)
        repositories = findViewById(R.id.rv_repositories)
    }

    fun setupListeners() {
        btnComfirmar.setOnClickListener {
            saveUserLocal()
            getAllReposByUserName()
        }
    }

    @SuppressLint("ResourceType")
    private fun saveUserLocal() {
        val sharePref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharePref.edit()) {
            putString(getString(R.string.user_name), userName.text.toString())
            apply()
        }
    }

    private fun showUserName(): String? {
        val sharePref = getPreferences(Context.MODE_PRIVATE)
        getAllReposByUserName()
        return sharePref.getString(getString(R.string.user_name), "User Name")
    }

    fun setupRetrofit() {
        val builder = Retrofit.Builder().baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        githubApi = builder.create(GitHubServices::class.java)
    }

    fun getAllReposByUserName() {
        githubApi.getAllRepositoriesByUser(userName.text.toString())
            .enqueue(object : Callback<List<Repository>> {
                override fun onResponse(
                    call: Call<List<Repository>>,
                    response: Response<List<Repository>>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            setupAdapter(it)
                        }
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Algo deu errado! Tente novamente mais tarde.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                override fun onFailure(call: Call<List<Repository>>, t: Throwable) {}
            })
    }

    fun setupAdapter(list: List<Repository>){
        val repoAdapter = RepositoryAdapter(list).apply {
            btnShareLister = {r -> shareRepositoryLink(r.htmlUrl)}
            repoItemLister= {r -> openBrowser(r.htmlUrl)}
        }
        repositories.apply {
            isVisible = true
            adapter = repoAdapter
        }
    }

    // Metodo responsavel por compartilhar o link do repositorio selecionado
    // @Todo 11 - Colocar esse metodo no click do share item do adapter
    fun shareRepositoryLink(urlRepository: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlRepository)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    // Metodo responsavel por abrir o browser com o link informado do repositorio
    // @Todo 12 - Colocar esse metodo no click item do adapter
    fun openBrowser(urlRepository: String) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlRepository)
            )
        )
    }

}