package br.com.iftm.edu.nostresswedding.data.repository

import br.com.iftm.edu.nostresswedding.data.mappers.toUserDto
import br.com.iftm.edu.nostresswedding.domain.models.UserDto
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
        onSuccess: (String, UserDto) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid
                val user = result.user?.toUserDto()
                if (uid != null && user != null) {
                    onSuccess(uid, user)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}