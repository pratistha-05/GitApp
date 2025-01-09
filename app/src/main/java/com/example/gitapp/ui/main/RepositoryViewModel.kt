package com.example.gitapp.ui.main


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gitapp.data.model.UserData
import com.example.gitapp.repository.UserRepository
import com.example.gitapp.utils.Result
import kotlinx.coroutines.launch

class RepositoryViewModel(private val repository: UserRepository) : ViewModel() {

  val userRepositories: MutableLiveData<Result<List<UserData>>> = MutableLiveData()

  fun fetchRepositories(username: String) {
    viewModelScope.launch {
      userRepositories.postValue(Result.Loading())
      val result = repository.getUserRepositories(username)
      userRepositories.postValue(result)
    }
  }
}
