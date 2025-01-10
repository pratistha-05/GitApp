package com.example.gitapp.ui.main.userList

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gitapp.R
import com.example.gitapp.di.RetrofitInstance
import com.example.gitapp.repository.RepoRepository
import com.example.gitapp.repository.UserRepository
import com.example.gitapp.ui.main.ViewModelFactory
import com.example.gitapp.ui.main.userRepository.RepositoriesActivity
import com.example.gitapp.utils.Result
class MainActivity : AppCompatActivity() {

  private lateinit var recyclerView: RecyclerView
  private lateinit var adapter: UserAdapter
  private lateinit var searchView: SearchView
  private lateinit var viewModel: UsersViewModel
  private lateinit var layout: LinearLayout
  private lateinit var swipeRefreshLayout: SwipeRefreshLayout

  private var currentPage = 1
  private val perPage = 20
  private var isLoading = false
  private var isLastPage = false
  private var currentQuery = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main_activity)

    layout = findViewById(R.id.layout_empty_state)
    recyclerView = findViewById(R.id.recyclerView)
    searchView = findViewById(R.id.search_view)
    swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)

    initRecyclerView()
    initViewModel()
    initPaginationListner()
    initSwipeRefreshListener()

    viewModel.userList.observe(this, Observer { result ->
      when (result) {
        is Result.Loading -> {
          if (!swipeRefreshLayout.isRefreshing) {
            recyclerView.visibility = View.VISIBLE
            layout.visibility = View.GONE
            adapter.showLoading()
          }
        }
        is Result.Success -> {
          swipeRefreshLayout.isRefreshing = false
          recyclerView.visibility = View.VISIBLE
          layout.visibility = View.GONE
          adapter.hideLoading()
          result.data?.let {
            if (it.size < perPage) isLastPage = true
            if (currentPage == 1) {
              adapter.updateData(it)
            } else {
              adapter.appendData(it)
            }
          }
        }
        is Result.Error -> {
          swipeRefreshLayout.isRefreshing = false
          adapter.hideLoading()
          if (result.message == "No users found") {
            layout.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
          }
        }
      }
    })

    searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
      override fun onQueryTextSubmit(query: String?): Boolean {
        if (!query.isNullOrEmpty()) {
          currentQuery = query
          resetPagination()
          viewModel.searchUsers(currentQuery, currentPage, perPage)
        }
        return true
      }

      override fun onQueryTextChange(newText: String?): Boolean = true
    })
  }

  private fun initRecyclerView() {
    recyclerView.layoutManager = LinearLayoutManager(this)
    adapter = UserAdapter(emptyList()) { username ->
      val intent = Intent(this, RepositoriesActivity::class.java)
      intent.putExtra("username", username)
      startActivity(intent)
    }
    recyclerView.adapter = adapter
  }

  private fun initViewModel() {
    val userRepository = UserRepository()
    val repositoryRepository = RepoRepository(RetrofitInstance.api)

    viewModel = ViewModelProvider(
      this,
      ViewModelFactory(userRepository, repositoryRepository)
    ).get(UsersViewModel::class.java)
  }

  private fun initPaginationListner() {
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
      override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

        if (!isLoading && !isLastPage && totalItemCount <= (lastVisibleItem + 5)) {
          isLoading = true
          currentPage++
          viewModel.searchUsers(currentQuery, currentPage, perPage)
        }
      }
    })
  }

  private fun initSwipeRefreshListener() {
    swipeRefreshLayout.setOnRefreshListener {
      resetPagination()
      viewModel.searchUsers(currentQuery, currentPage, perPage)
    }
  }

  private fun resetPagination() {
    currentPage = 1
    isLastPage = false
    isLoading = false
    adapter.updateData(emptyList())
  }
}
