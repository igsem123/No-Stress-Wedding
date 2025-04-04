package br.com.iftm.edu.nostresswedding.domain.models

data class UserDto(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val weddingDate: String,
    val weddingLocation: String,
    val weddingTheme: String,
    val weddingBudget: String,
    val weddingGuestList: List<String>,
    val weddingVendors: List<String>,
    val weddingTasks: List<String>,
    val weddingPayments: List<String>,
)