package br.com.iftm.edu.nostresswedding.data.repository

import br.com.iftm.edu.nostresswedding.data.local.database.NSWeddingDatabase
import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

/**
 * Repositório para gerenciar os dados do usuário.
 *
 * @property firestore Instância do FireStore para acessar o Firestore database.
 * @property db Instância do Room para acessar o local database.
 */

class UserRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val db: NSWeddingDatabase
) {
    fun getUserFromFirestore(uid: String, onSuccess: (Map<String, Any>) -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("users").document(uid).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    onSuccess(document.data ?: emptyMap())
                } else {
                    onFailure(Exception("No such document"))
                }
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    suspend fun getUserFromRoom(uid: String): UserEntity? {
        val userEntity = db.userDao().getUserById(uid)
        return userEntity?.let {
            UserEntity(
                uid = it.uid,
                email = it.email,
                name = it.name,
                phone = it.phone,
                username = it.username,
                weddingBudget = it.weddingBudget,
                weddingDate = it.weddingDate
            )
        }
    }

    suspend fun saveOrUpdateUserInRoom(userEntity: UserEntity) {
        coroutineScope {
            db.userDao().upsertUser(userEntity)
        }
    }
}