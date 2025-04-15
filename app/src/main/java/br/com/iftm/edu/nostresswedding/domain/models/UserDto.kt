package br.com.iftm.edu.nostresswedding.domain.models

data class UserDto(
    val uid: String,
    val name: String,
    val email: String,
    val phone: String?,
    val username: String?,
    val weddingBudget: String,
    val weddingDate: String
)