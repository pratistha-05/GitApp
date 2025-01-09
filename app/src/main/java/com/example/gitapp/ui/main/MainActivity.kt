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

  private var currentPage = 1
  private val perPage = 20
  private var isLoading = false
  private var currentQuery = ""

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
          // Show loading indicator
        }
        is Result.Success -> {
          result.data?.let {
            if (currentPage == 1) {
              adapter.updateData(it)
            } else {
              adapter.appendData(it)
            }
            adapter.notifyDataSetChanged()
          }
          isLoading = false
        }
        is Result.Error -> {
          // Show error message
        }
      }
    })

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
          currentQuery = query
          currentPage = 1
          viewModel.searchUsers(currentQuery, currentPage, perPage)
        }
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean = true
    })

    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (!isLoading && totalItemCount <= (lastVisibleItem + 5)) {
          currentPage++
          isLoading = true
          viewModel.searchUsers(currentQuery, currentPage, perPage)
        }
      }
    })
  }
}

