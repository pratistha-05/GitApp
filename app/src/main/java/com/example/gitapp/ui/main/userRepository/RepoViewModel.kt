package com.example.gitapp.ui.main.userRepository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitapp.data.model.RepoResponse
import com.example.gitapp.repository.RepoRepository
import kotlinx.coroutines.launch
import com.example.gitapp.utils.Result


class RepoViewModel(private val repository: RepoRepository) : ViewModel() {

  val repositoryList: MutableLiveData<Result<List<RepoResponse>>> = MutableLiveData()

  fun getUserRepositories(username: String) {
    viewModelScope.launch {
      repositoryList.postValue(Result.Loading())
      val result = repository.getUserRepositories(username)
      repositoryList.postValue(result)
    }
  }
}
