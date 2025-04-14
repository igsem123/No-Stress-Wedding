package br.com.iftm.edu.nostresswedding.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.iftm.edu.nostresswedding.data.local.database.NSWeddingDatabase
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val db: NSWeddingDatabase
): ViewModel() {

    private val _uiState = mutableStateOf(RegisterUiState())
    val uiState: State<RegisterUiState> = _uiState

    fun onFieldChange(field: String, value: String) {
        _uiState.value = when (field) {
            "username" -> _uiState.value.copy(username = value)
            "password" -> _uiState.value.copy(password = value)
            "confirmPassword" -> _uiState.value.copy(confirmPassword = value)
            "name" -> _uiState.value.copy(name = value)
            "weddingDate" -> _uiState.value.copy(weddingDate = value)
            "weddingBudget" -> _uiState.value.copy(weddingBudget = value.toDoubleOrNull() ?: 0.0)
            else -> _uiState.value
        }
    }

    val firestore = FirebaseFirestore.getInstance()

    fun registerUser() {
        with(_uiState.value) {
            if (username.isBlank() || password.isBlank() || name.isBlank() ||
                weddingDate.isBlank() || weddingBudget <= 0.0
            ) {
                _uiState.value = _uiState.value.copy(error = "Preencha todos os campos corretamente")
                return
            }

            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(username, password)
                .addOnSuccessListener { result ->
                    val uid = result.user?.uid ?: return@addOnSuccessListener
                    val userEntity = UserEntity(
                        uid = uid,
                        email = username,
                        name = name,
                        weddingDate = weddingDate,
                        weddingBudget = weddingBudget.toDouble()
                    )

                    // Salva no Firestore
                    firestore.collection("users").document(uid).set(userEntity)
                        .addOnSuccessListener {
                            // Salva no Room
                            viewModelScope.launch(Dispatchers.IO) {
                                db.userDao().insertUser(userEntity)
                                _uiState.value = _uiState.value.copy(isLoading = false, success = true)
                            }

                            clearError() // Limpa o erro após o sucesso
                        }
                        .addOnFailureListener {
                            _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
                        }

                }
                .addOnFailureListener {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
                }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}

data class RegisterUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val weddingDate: String = "",
    val weddingBudget: Double = 0.0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
