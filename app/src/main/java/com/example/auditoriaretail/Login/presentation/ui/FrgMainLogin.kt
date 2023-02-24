package com.example.auditoriaretail.Login.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.auditoriaretail.Login.data.model.LoginModel
import com.example.auditoriaretail.Login.presentation.vm.LoginViewModel
import com.example.auditoriaretail.R
import com.example.auditoriaretail.databinding.FrgMainLoginBinding
import com.example.auditoriaretail.util.FormValidation.validateEmail
import com.example.auditoriaretail.util.FormValidation.validatePassword
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executor


class FrgMainLogin : Fragment() {

    private lateinit var executor: Executor;

    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo

    //Login
    private val viewModel: LoginViewModel by viewModels()
    private val GOOGLE_SING_IN_ = 1000
    private lateinit var firebaseAuth: FirebaseAuth
    //Binding
    private lateinit var binding:FrgMainLoginBinding //Agregar todos los ID que hay en las vistas

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FrgMainLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {//Sobreescribir el ciclo
        super.onViewCreated(view, savedInstanceState)

        //START LOGIN
        with(binding){
            inputEmail.validate(txtInputEmail, ::validateEmail)
            inputPassword.validate(txtInputPassword, ::validatePassword)
        }

        //BiometricData
        executor=ContextCompat.getMainExecutor(requireContext())
        biometricPrompt=androidx.biometric.BiometricPrompt(this@FrgMainLogin,executor,object:androidx.biometric.BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(requireContext(), "Demasiados intentos, Ingresa con contraseña", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                findNavController().navigate(R.id.to_frgDashboard)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(requireContext(), "Huella no reconocida", Toast.LENGTH_SHORT).show()
            }
        })
        promptInfo=androidx.biometric.BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autentificación Retail")
            .setSubtitle("Ingresa tu Huella")
            .setNegativeButtonText("Regresar al Inicio")
            .build()
        //EndBiometricData

        binding.btnIniciarSesion.setOnClickListener {
            viewModel.login(
                LoginModel(
                    binding.inputEmail.text.toString(),
                    binding.inputPassword.text.toString(),
                ),
            )
            biometricPrompt.authenticate(promptInfo)
        }

        binding.btnRegistrar.setOnClickListener {// Funcion del boton para enviar a la pantalla de registro
            findNavController().navigate(R.id.to_frg_register)
        }

    }

    //Funcion para validar campos
    private fun EditText.validate(
        input: TextInputLayout,
        validate: (String) -> Boolean
    ) {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                if (!validate(text.toString())) {
                    //input.error = getString(R.string.form_invalidate)
                    input.error = "Formulario invalido"
                    binding.btnIniciarSesion.background = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.round
                    )
                } else {
                    input.error = null
                    validateError()
                }
            }
        })
    }

    private fun validateError() {
        with(binding) {
            if (
                txtInputEmail.error == null && inputEmail.text.toString().isNotEmpty() &&
                txtInputPassword.error == null && inputPassword.text.toString().isNotEmpty()
            ) {
                binding.btnIniciarSesion.background = ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.round_enable,
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SING_IN_) {
            viewModel.loginGoogle(data!!)
        }
    }

}