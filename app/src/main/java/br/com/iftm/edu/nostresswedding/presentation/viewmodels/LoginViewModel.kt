package br.com.iftm.edu.nostresswedding.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    // StateFlow para gerenciar o estado do email e senha
    private val _email = MutableStateFlow("")
    val email: StateFlow<String>
        get() = _email

    private val _password = MutableStateFlow("")
    val password: StateFlow<String>
        get() = _password
}