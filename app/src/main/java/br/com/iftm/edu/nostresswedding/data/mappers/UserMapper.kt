package br.com.iftm.edu.nostresswedding.data.mappers

import br.com.iftm.edu.nostresswedding.data.local.entity.UserEntity
import br.com.iftm.edu.nostresswedding.domain.models.UserDto
import com.google.firebase.auth.FirebaseUser
import com.google.firestore.v1.FirestoreGrpc

/**
 * Mapeia um objeto [UserEntity] para um objeto [UserDto].
 *
 * @return Um objeto [UserDto] com os mesmos dados do objeto [UserEntity].
 */

fun UserEntity.toUserDto(): UserDto {
    return UserDto(
        uid = uid,
        name = name,
        email = email,
        phone = phone,
        username = username,
        weddingBudget = weddingBudget,
        weddingDate = weddingDate
    )
}

fun UserDto.toUserEntity(): UserEntity {
    return UserEntity(
        uid = uid,
        name = name,
        email = email,
        phone = phone ?: "",
        username = username ?: "",
        weddingBudget = weddingBudget,
        weddingDate = weddingDate
    )
}

fun FirebaseUser.toUserDto(): UserDto {
    return UserDto(
        uid = uid,
        name = displayName ?: "",
        email = email ?: "",
        phone = phoneNumber,
        username = displayName,
        weddingBudget = "",
        weddingDate = ""
    )
}