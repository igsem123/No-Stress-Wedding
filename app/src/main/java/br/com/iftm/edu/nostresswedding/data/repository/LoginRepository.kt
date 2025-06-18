package br.com.iftm.edu.nostresswedding.data.repository

import android.util.Log
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Repositório para gerenciar o login de usuários.
 *
 * @param userRepository Instância do repositório de usuários.
 */

class LoginRepository @Inject constructor(
    private val userRepository: UserRepository
) {
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signInWithEmailAndUser(
        email: String,
        password: String,
        onSuccess: (UserEntity) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid
                result.user?.let { user ->
                    userRepository.getUserFromFirestore(
                        uid = uid.toString(),
                        onSuccess = { onSuccess(it) },
                        onFailure = { exception ->
                            Log.e(
                                "LoginRepository",
                                "Error fetching user data: ${exception.message}"
                            )
                            onFailure(exception)
                        }
                    )
                }
            }
    }
}