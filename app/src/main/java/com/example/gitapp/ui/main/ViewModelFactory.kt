package com.example.gitapp.ui.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitapp.repository.RepoRepository
import com.example.gitapp.repository.UserRepository
import com.example.gitapp.ui.main.userList.UsersViewModel
import com.example.gitapp.ui.main.userRepository.RepoViewModel

class ViewModelFactory(
  private val userRepository: UserRepository,
  private val repositoryRepository: RepoRepository
) : ViewModelProvider.Factory {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    return when {
      modelClass.isAssignableFrom(UsersViewModel::class.java) -> {
        UsersViewModel(userRepository) as T
      }
      modelClass.isAssignableFrom(RepoViewModel::class.java) -> {
        RepoViewModel(repositoryRepository) as T
      }
      else -> throw IllegalArgumentException("Unknown ViewModel class")
    }
  }
}

