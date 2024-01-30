package ueno.dio.githubsearch.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ueno.dio.githubsearch.R
import ueno.dio.githubsearch.domain.Repository

class RepositoryAdapter(private val repositories: List<Repository>) :
    RecyclerView.Adapter<RepositoryAdapter.ViewHolder>() {

    var repoItemLister: (Repository) -> Unit = {}
    var btnShareLister: (Repository) -> Unit = {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.repository_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RepositoryAdapter.ViewHolder, position: Int) {
        holder.name.text = repositories[position].name
        holder.btn.setOnClickListener {
            btnShareLister(repositories[position])
        }
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val btn: ImageView

        init {
            view.apply {
                name = findViewById(R.id.tv_name_repository)
                btn = findViewById(R.id.iv_share)
            }
        }

    }
}