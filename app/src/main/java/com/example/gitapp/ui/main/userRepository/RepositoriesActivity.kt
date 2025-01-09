package com.example.gitapp.ui.main.userRepository

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapp.R
import com.example.gitapp.di.RetrofitInstance
import com.example.gitapp.repository.RepoRepository
import com.example.gitapp.repository.UserRepository
import com.example.gitapp.ui.main.ViewModelFactory
import com.example.gitapp.utils.Result

class RepositoriesActivity : AppCompatActivity() {

  private lateinit var recyclerView: RecyclerView
  private lateinit var adapter: RepositoryAdapter
  private lateinit var viewModel: RepoViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_repositories)

    val username = intent.getStringExtra("username") ?: return

    recyclerView = findViewById(R.id.rv_repo_list)
    recyclerView.layoutManager = LinearLayoutManager(this)

    adapter = RepositoryAdapter(emptyList())
    recyclerView.adapter = adapter

    val userRepository = UserRepository()
    val repositoryRepository = RepoRepository(RetrofitInstance.api)

     viewModel = ViewModelProvider(
      this,
      ViewModelFactory(userRepository, repositoryRepository)
    ).get(RepoViewModel::class.java)

    viewModel.repositoryList.observe(this, Observer { result ->
      when (result) {
        is Result.Loading -> {
          adapter.showLoading()
        }
        is Result.Success -> {
          adapter.hideLoading()
          result.data?.let {
              adapter.updateData(it)
            }
          }

        is Result.Error -> {
          //TODO: Handle error
          adapter.hideLoading()
        }
      }
    })

    viewModel.getUserRepositories(username)
  }
}
