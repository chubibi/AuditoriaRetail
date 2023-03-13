package com.example.auditoriaretail.Dashboard.presentation.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.AdaptadorNombres
import com.example.auditoriaretail.R
import com.example.auditoriaretail.databinding.FrgBuscadorBinding
import com.example.auditoriaretail.databinding.FrgDashboardBinding
import com.example.auditoriaretail.databinding.FrgMainLoginBinding
import java.util.*
import kotlin.collections.ArrayList

class FragmentBuscador : Fragment() {
    private lateinit var binding: FrgBuscadorBinding
    private lateinit var adaptador: AdaptadorNombres

    var listaNombres = arrayListOf("[60]-CHEDRAUI ACAPULCO CAYACO", "[138]-CHEDRAUI SELECTO ACAPULCO DIAMANTE" ,
        "[254]-CHEDRAUI ACAPULCO PIE DE LA CUESTA" , "[265]-CHEDRAUI ACAPULCO COSTERA" ,
        "[620]-SUPER CHEDRAUI CHILAPA" , "[621]-SUPER CHEDRAUI ATOYAC" , "[625]-SUPER CHEDRAUI TLAPA DE COMONFORT" ,
        "[633]-SUPER CHEDRAUI OMETEPEC" , "[685]-SUPER CHEDRAUI SANTIAGO PINOTEPA NACIONAL" ,
        "[122]-CHEDRAUI LA PAZ SANTA FE" , "[126]-CHEDRAUI LA PAZ PALACIO" ,
        "[127]-CHEDRAUI LA PAZ COLIMA" , "[128]-CHEDRAUI CABO SAN LUCAS" ,
        "[133]-CHEDRAUI SAN JOSE DEL CABO" , "[178]-CHEDRAUI SELECTO SAN JOSE DEL CABO CAMPESTRE" ,
        "[189]-CHEDRAUI CABO NORTE" , "[41]-CHEDRAUI CAMPECHE SANTA ANA" ,
        "[69]-CHEDRAUI CHETUMAL INSURGENTES" , "[102]-CHEDRAUI CAMPECHE VILLA TURQUESA" ,
        "[118]-CHEDRAUI CHETUMAL MULTIPLAZA" , "[610]-SUPER CHEDRAUI ESCARCEGA" ,
        "[615]-CHEDRAUI HUATULCO" , "[617]-CHEDRAUI PUERTO ESCONDIDO" ,
        "[618]-SUPER CHEDRAUI CHAMPOTON" )

    private val startActivityForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            var result = it.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            binding.etNombre.setText(result!![0])
        }
    }
    // Declarar Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FrgBuscadorBinding.inflate(inflater,container,false)
        return binding.root

    }


// Crear el orrive
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNombres.layoutManager = LinearLayoutManager(requireContext())
        adaptador = AdaptadorNombres(listaNombres)
        binding.rvNombres.adapter = adaptador
        binding.ibtnMicrofono.setOnClickListener {
            binding.etNombre.setText("")
            escucharVoz()
        }

        binding.etNombre.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

            override fun afterTextChanged(p0: Editable?) {
                filtrar(p0.toString())
            }

        })
    }



    fun escucharVoz() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())


        if (getActivity()?.let { intent.resolveActivity(it.getPackageManager()) } != null) {
            startActivityForResult.launch(intent)
        } else {
            Log.e("ERROR", "Su dispositivo no admite entrada por voz")
            Toast.makeText(requireContext(), "Su dispositivo no admite entrada por voz", Toast.LENGTH_LONG).show()
        }
    }

    fun filtrar(texto: String) {
        val listaFiltrada: ArrayList<String> = arrayListOf()

        for (nombre in listaNombres) {
            if (nombre.toLowerCase().contains(texto.toLowerCase())) {
                listaFiltrada.add(nombre)
            }
        }
        adaptador.filtrar(listaFiltrada)
    }
}