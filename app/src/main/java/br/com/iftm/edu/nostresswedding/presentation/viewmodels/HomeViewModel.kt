package br.com.iftm.edu.nostresswedding.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import br.com.iftm.edu.nostresswedding.data.repository.LoginRepository
import br.com.iftm.edu.nostresswedding.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val loginRepository: LoginRepository
) : ViewModel() {

    private var _user: MutableStateFlow<UserEntity?> = MutableStateFlow(null)
    val user: MutableStateFlow<UserEntity?>
        get() = _user

    fun getUserDataFromRoom(
        uid: String
    ) {
        /*viewModelScope.launch(Dispatchers.IO) {
            _user = userRepository.getUserFromRoom(uid)
        }*/
    }

    fun logout() {
        loginRepository.auth.signOut()
    }
}