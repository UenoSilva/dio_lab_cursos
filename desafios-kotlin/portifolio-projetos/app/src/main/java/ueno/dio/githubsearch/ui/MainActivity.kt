package ueno.dio.githubsearch.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import ueno.dio.githubsearch.R

class MainActivity : AppCompatActivity() {

    lateinit var userName: EditText
    lateinit var btnComfirmar: Button
    lateinit var repositories: RecyclerView
    lateinit var githubApi: GitHubServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setupView(){
        userName = findViewById(R.id.et_name_user)
        btnComfirmar = findViewById(R.id.bt_search)
        repositories = findViewById(R.id.rv_repositories)
    }

}