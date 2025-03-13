package com.sumaqada.vocabulary.service

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialRequest.Builder
import androidx.credentials.GetCredentialResponse
import androidx.credentials.PasswordCredential
import androidx.credentials.PublicKeyCredential
import com.google.android.libraries.identity.googleid.GetSignInWithGoogleOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.sumaqada.vocabulary.R
import com.sumaqada.vocabulary.repository.UserData
import com.sumaqada.vocabulary.ui.home.TAG
import kotlinx.coroutines.tasks.await

class AuthGoogleServiceImpl(
    private val context: Context,
    private val authFirebase: FirebaseAuth
) : AuthService {

    private val signInWithGoogleOption: GetSignInWithGoogleOption = GetSignInWithGoogleOption
        .Builder(context.getString(R.string.web_api_key))
//        .setNonce("")
        .build()


    private val request: GetCredentialRequest = Builder()
        .addCredentialOption(signInWithGoogleOption)
        .build()

    private fun handleSignIn(result: GetCredentialResponse): String {

        val credential = result.credential

        when (credential) {
            is PublicKeyCredential -> {
                Log.i(TAG, "handleSignIn: Public key credential")
            }

            is PasswordCredential -> {
                Log.i(TAG, "handleSignIn: Password Credential")

            }

            is CustomCredential -> {
                Log.i(TAG, "handleSignIn: Custom Credential")
                if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    try {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)
                        return googleIdTokenCredential.idToken

                    } catch (e: GoogleIdTokenParsingException) {
                        Log.e(TAG, "Received an invalid google id token response", e)
                    }
                }

            }

            else -> {
                Log.e(TAG, "handleSignIn: Unexpected type of credential")
            }
        }
        throw Exception("No credentials")

    }

    override suspend fun getUserData(): UserData? {
        Log.i(TAG, "getUserData: loading")
        return authFirebase.currentUser?.toUserData()
    }

    override suspend fun singIn(contextActivity: Context): UserData? {
        val result = CredentialManager.create(contextActivity).getCredential(contextActivity, request)
        val googleIdToken = handleSignIn(result)

        return firebaseAuthWithGoogle(googleIdToken)
    }

    private suspend fun firebaseAuthWithGoogle(idToken: String): UserData? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val user = authFirebase.signInWithCredential(credential)
            .await()
            .user
        return user?.toUserData()
    }

    override suspend fun singOut() {
        authFirebase.signOut()
    }

}

fun FirebaseUser.toUserData() = UserData(
    this.uid, this.displayName, this.email, this.photoUrl.toString()
)
