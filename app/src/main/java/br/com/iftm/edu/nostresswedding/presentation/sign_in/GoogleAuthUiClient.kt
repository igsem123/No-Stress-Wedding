package br.com.iftm.edu.nostresswedding.presentation.sign_in

import android.content.Context
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class GoogleAuthUiClient(
    private val context: Context
) {
    val WEB_CLIENT_ID = "680995951918"

    // Instantiate a Google sign-in request
    val googleIdOption = GetGoogleIdOption.Builder()
        // Your server's client ID, not your Android client ID.
        .setServerClientId(WEB_CLIENT_ID)
        .setAutoSelectEnabled(true)
        // Only show accounts previously used to sign in.
        .setFilterByAuthorizedAccounts(true)
        .build()

    // Create the Credential Manager request
    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    var auth = Firebase.auth
    fun signIn() {
        // Implementar a lógica de autenticação com o Google
        // Usar o oneTapClient para iniciar o fluxo de autenticação
    }

    fun signOut() {
        // Implementar a lógica de logout
        // Usar o oneTapClient para fazer logout do usuário
    }
}