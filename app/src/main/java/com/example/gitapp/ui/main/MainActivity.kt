package com.example.gitapp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
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
  private lateinit var viewModel: UsersViewModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_activity)

    recyclerView = findViewById(R.id.recyclerView)
    searchView = findViewById(R.id.search_view)
    val repository = UserRepository()
    viewModel = ViewModelProvider(this, ViewModelFactory(repository)).get(UsersViewModel::class.java)

    recyclerView.layoutManager = LinearLayoutManager(this)
    adapter = UserAdapter(emptyList())
    recyclerView.adapter = adapter

    viewModel.userList.observe(this, Observer { result ->
      when (result) {
        is Result.Loading -> {
          // Show loading
        }
        is Result.Success -> {
          result.data?.let {
            adapter.updateData(it)
            adapter.notifyDataSetChanged()
          }
        }
        is Result.Error -> {
          // Show error
        }
      }
    })

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
          viewModel.searchUsers(query)
        }
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean = true
    })
  }
}
