package br.com.iftm.edu.nostresswedding.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import br.com.iftm.edu.nostresswedding.data.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel para gerenciar o estado do registro
 * @param repository Repositório para autenticação de registro
 */

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: RegisterRepository
): ViewModel() {

    private val _formState = mutableStateOf(RegisterUiState())
    val formState: State<RegisterUiState> = _formState

    private val _userRegistrationState = mutableStateOf<UserRegistrationState>(UserRegistrationState.Idle)
    val userRegistrationState: State<UserRegistrationState> = _userRegistrationState

    fun onFieldChange(field: String, value: String) {
        _formState.value = when (field) {
            "username" -> _formState.value.copy(username = value)
            "password" -> _formState.value.copy(password = value)
            "confirmPassword" -> _formState.value.copy(confirmPassword = value)
            "name" -> _formState.value.copy(name = value)
            "weddingDate" -> _formState.value.copy(weddingDate = value)
            "weddingBudget" -> _formState.value.copy(weddingBudget = value)
            else -> _formState.value
        }
    }

    fun isPasswordEqual(): Boolean {
        return _formState.value.password == _formState.value.confirmPassword
    }

    fun registerUser() {
        with(_formState.value) {
            if (username.isBlank() || password.isBlank() || name.isBlank() ||
                weddingDate.isBlank() || weddingBudget.isBlank()
            ) {
                _userRegistrationState.value = UserRegistrationState.Error("Preencha todos os campos corretamente!")
                return
            }

           _userRegistrationState.value = UserRegistrationState.Loading

            repository.createUserInFirebase(username, password, { uid ->
                val userEntity = UserEntity(
                    uid = uid,
                    email = username,
                    name = name,
                    weddingDate = weddingDate,
                    weddingBudget = weddingBudget
                )

                repository.saveUserInFirestore(uid, userEntity, {
                    viewModelScope.launch(Dispatchers.IO) {
                        repository.saveUserInRoom(userEntity)
                        _userRegistrationState.value = UserRegistrationState.Success(uid)
                    }
                    clearForm()
                }, { exception ->
                    _userRegistrationState.value = UserRegistrationState.Error(exception.message ?: "Erro ao salvar usuário no banco de dados!")
                })
            }, { exception ->
                _userRegistrationState.value = UserRegistrationState.Error(exception.message ?: "Erro ao criar usuário!")
            })
        }
    }

    fun clearForm() {
        _formState.value = RegisterUiState()
    }

    fun resetRegistrationState() {
        _userRegistrationState.value = UserRegistrationState.Idle
    }
}

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val weddingDate: String = "",
    val weddingBudget: String = ""
)

sealed class UserRegistrationState {
    object Idle : UserRegistrationState()
    object Loading : UserRegistrationState()
    data class Success(val userId: String) : UserRegistrationState()
    data class Error(val message: String) : UserRegistrationState()
}
