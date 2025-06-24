package br.com.iftm.edu.nostresswedding.data.repository

import android.util.Log
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
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

    suspend fun signInWithEmailAndPassword(
        email: String,
        password: String
    ) : FirebaseUser? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            Log.e("LoginRepository", "Erro ao logar: ${e.message}")
            null
        }
    }
}