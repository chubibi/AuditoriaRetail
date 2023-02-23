package com.example.auditoriaretail.Login.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.auditoriaretail.R
import com.example.auditoriaretail.databinding.FrgMainLoginBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.util.concurrent.Executor


class FrgMainLogin : Fragment() {

    private lateinit var executor: Executor;
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo

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




        val user:MutableMap<String,Any> = HashMap()// La llave va hacer el string y el valor sera lo que quieras
        user["password"] = "1234"
        user["email"] = "jonaulig@gmail.com"
        user["telefono"] = 643757473
        //Testing firestore
        /*FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(requireContext(), "->${it.id.toString()}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Log.d("FIREB","Error en firebase")
            } */


        binding.btnIniciarSesion.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)

        }

        binding.btnRegistrar.setOnClickListener {// Funcion del boton para enviar a la pantalla de registro
            findNavController().navigate(R.id.to_frg_register)
        }

    }

}