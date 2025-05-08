package br.com.iftm.edu.nostresswedding.data.repository

import br.com.iftm.edu.nostresswedding.data.local.database.NSWeddingDatabase
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

/**
 * Repositório para gerenciar o registro de usuários.
 *
 * @param firestore Instância do Firestore para acessar o banco de dados Firestore.
 * @param db Instância do Room para acessar o banco de dados local.
 */

class RegisterRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val db: NSWeddingDatabase
) {
    fun createUserInFirebase(username: String, password: String, onSuccess: (String) -> Unit, onFailure: (Exception) -> Unit) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(username, password)
            .addOnSuccessListener { result ->
                val uid = result.user?.uid
                if (uid != null) {
                    onSuccess(uid)
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun saveUserInFirestore(uid: String, userEntity: UserEntity, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("users").document(uid).set(userEntity)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    suspend fun saveUserInRoom(userEntity: UserEntity) {
        db.userDao().insertUser(userEntity)
    }
}