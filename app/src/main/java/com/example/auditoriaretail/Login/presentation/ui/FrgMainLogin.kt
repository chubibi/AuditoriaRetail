package com.example.auditoriaretail.Login.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.auditoriaretail.R
import com.example.auditoriaretail.databinding.FrgMainLoginBinding
import com.google.firebase.firestore.FirebaseFirestore


class FrgMainLogin : Fragment() {

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

        binding.btnRegistrar.setOnClickListener {// Funcion del boton para enviar a la pantalla de registro
            findNavController().navigate(R.id.to_frg_register)
        }
    }

}