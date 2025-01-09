package com.example.gitapp.ui.main


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.gitapp.repository.UserRepository

class ViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
      return UsersViewModel(repository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}
