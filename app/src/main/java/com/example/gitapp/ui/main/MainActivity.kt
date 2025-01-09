package com.example.gitapp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gitapp.R
import com.example.gitapp.repository.UserRepository
import com.example.gitapp.utils.Result

class MainActivity : AppCompatActivity() {

  private lateinit var recyclerView: RecyclerView
  private lateinit var adapter: UserAdapter
  private lateinit var searchView: SearchView
  private lateinit var viewModel: RepositoryViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_activity)

    recyclerView = findViewById(R.id.recyclerView)
    searchView = findViewById(R.id.searchView)
    val repository = UserRepository()
    viewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(RepositoryViewModel::class.java)


    recyclerView.layoutManager = LinearLayoutManager(this)
    adapter = UserAdapter(emptyList())
    recyclerView.adapter = adapter


    viewModel.userRepositories.observe(this) { resource ->
      when (resource) {
        is Result.Loading -> {
          Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
        }
        is Result.Success -> {
          resource.data?.let {
            adapter.updateData(it)
            adapter.notifyDataSetChanged()
          }
        }
        is Result.Error -> {
          Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
        }
      }
    }

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
          viewModel.fetchRepositories(query)
        }
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean = true
    })
  }
}
