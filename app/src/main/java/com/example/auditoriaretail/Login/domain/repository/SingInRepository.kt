package com.example.teamro.home.presentation.domain.repository

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.example.auditoriaretail.FirebaseClient
import com.example.auditoriaretail.SuccessResult
import com.example.auditoriaretail.util.ErrorApp
import com.example.auditoriaretail.Login.data.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject
import com.example.auditoriaretail.Error
import com.example.auditoriaretail.Result


class SingInRepository @Inject constructor(private val firebaseClient: FirebaseClient) {
    fun saveGoogle(data: Intent?): MutableLiveData<Result<UserModel>> {
        val mutableData = MutableLiveData<Result<UserModel>>()
        try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                firebaseClient.dbAuth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful && it.result.user != null) {
                        val userModel = UserModel(
                            it.result.user?.displayName ?: "",
                            it.result.user?.email ?: "",
                            "",
                            it.result.user?.phoneNumber ?: "",
                            singIn = 2,
                            uuid = it.result.user?.uid ?: "",
                        )
                        mutableData.value = SuccessResult(userModel)
                    } else {
                        mutableData.value = Error(ErrorApp.ERROR_GOOGLE)
                    }
                }
            } else {
                mutableData.value = Error(ErrorApp.ERROR_GOOGLE)
            }
        } catch (e: Exception) {
            mutableData.value = Error(e.message.toString())
        }
        return mutableData
    }
}
