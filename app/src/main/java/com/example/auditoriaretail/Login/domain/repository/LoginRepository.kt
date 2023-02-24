package com.example.auditoriaretail.Login.domain.repository


import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.example.auditoriaretail.FirebaseClient
import com.example.auditoriaretail.Login.data.model.LoginModel
import com.example.auditoriaretail.Login.data.model.UserModel
import com.example.auditoriaretail.SuccessResult
import com.example.auditoriaretail.util.ErrorApp.ERROR_DATABASE
import com.example.auditoriaretail.util.ErrorApp.ERROR_LOGIN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.DocumentSnapshot
import com.example.auditoriaretail.Error
import com.example.auditoriaretail.Result
import javax.inject.Inject

class LoginRepository @Inject constructor(private val firebaseClient: FirebaseClient) {
    fun login(loginData: LoginModel): MutableLiveData<Result<UserModel>> {
        val mutableData = MutableLiveData<Result<UserModel>>()
        try {
            with(firebaseClient) {
                dbAuth.signInWithEmailAndPassword(loginData.email, loginData.password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            db.collection("users").document(it.result.user?.uid ?: "").get()
                                .addOnCompleteListener { result ->
                                    if (result.isSuccessful && result.result.exists()) {
                                        mutableData.value = SuccessResult(createUser(result))
                                    } else {
                                        mutableData.value = Error(ERROR_DATABASE)
                                    }
                                }
                        } else {
                            mutableData.value = Error(ERROR_LOGIN)
                        }
                    }
            }
        } catch (e: Exception) {
            mutableData.value = Error(e.message.toString())
        }
        return mutableData
    }

    fun loginGoogle(data: Intent?): MutableLiveData<Result<UserModel>> {
        val mutableData = MutableLiveData<Result<UserModel>>()
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)
            if (account != null) {
                with(firebaseClient) {
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    dbAuth.signInWithCredential(credential).addOnCompleteListener {
                        if (it.isSuccessful) {
                            db.collection("users").document(it.result.user?.uid ?: "").get()
                                .addOnCompleteListener { result ->
                                    if (result.isSuccessful && result.result.exists()) {
                                        mutableData.value = SuccessResult(createUser(result))
                                    } else {
                                        mutableData.value = Error(ERROR_DATABASE)
                                    }
                                }
                        } else {
                            mutableData.value = Error(ERROR_LOGIN)
                        }
                    }
                }
            } else {
                mutableData.value = Error(ERROR_LOGIN)
            }
        } catch (e: Exception) {
            mutableData.value = Error(e.message.toString())
        }
        return mutableData
    }

    private fun createUser(resul: Task<DocumentSnapshot>): UserModel {
        return UserModel(
            resul.result.data?.get(
                "fullName",
            ).toString(),
            resul.result.data?.get(
                "email",
            ).toString(),
            resul.result.data?.get(
                "password",
            ).toString(),
            resul.result.data?.get(
                "phone",
            ).toString(),
        )
    }
}
