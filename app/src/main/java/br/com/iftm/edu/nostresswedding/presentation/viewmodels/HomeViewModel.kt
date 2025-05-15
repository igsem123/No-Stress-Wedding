package br.com.iftm.edu.nostresswedding.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.iftm.edu.nostresswedding.core.utils.toLongDate
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
        viewModelScope.launch(Dispatchers.IO) {
            _user.value = userRepository.getUserFromRoom(uid)
        }
    }

    fun logout() {
        loginRepository.auth.signOut()
    }

    fun getCountTillWeddingDay(weddingDate: String): Int {
        // Data no padrão 2034-07-10
        val currentDate = System.currentTimeMillis()
        val weddingDateInMillis = weddingDate.toLongDate()
        val diffInMillis = weddingDateInMillis - currentDate
        return (diffInMillis / (1000 * 60 * 60 * 24)).toInt()
    }

    fun getCountTillWeddingDayInString(weddingDate: String): String {
        val count = getCountTillWeddingDay(weddingDate)
        return if (count > 0) {
            "Faltam $count dias para o seu casamento!"
        } else {
            "Seu casamento já ocorreu! Parabéns!"
        }
    }
}