package com.example.auditoriaretail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.AdaptadorNombres
import com.example.auditoriaretail.databinding.ActivityUserlistBinding
import com.google.android.material.search.SearchView
import com.google.firebase.database.*

class UserlistActivity : AppCompatActivity() {
    private lateinit var adapter : MyAdapter
    private lateinit var binding: ActivityUserlistBinding
    private lateinit var dbref: DatabaseReference
    private lateinit var userRecyclerview: RecyclerView
    private lateinit var userArrayList: ArrayList<Datos>
    var eventListener:ValueEventListener?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserlistBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val userAdapter: ArrayAdapter<String> = ArrayAdapter(
            this, android.R.layout.simple_list_item_1
        )


        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)
        userArrayList = arrayListOf<Datos>()
        getUserData()

    }


    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Tiendas")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    for (userSnapshot in snapshot.children) {


                        val user = userSnapshot.getValue(Datos::class.java)
                        userArrayList.add(user!!)

                    }

                    userRecyclerview.adapter = MyAdapter(userArrayList)


                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        binding.searchView.setOnQueryTextListener(object :androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return false
            }
        })

    }
fun searchList(text: String){
    val searList = ArrayList<Datos>()
    for (datosclass in userArrayList){
        if (datosclass.Identificador?.lowercase()?.contains(text.lowercase())==true){
            searList.add(datosclass)
        }
    }
    adapter.searchDataList(searList)
}
}