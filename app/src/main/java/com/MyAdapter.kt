package com.example.auditoriaretail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private var userList : ArrayList<Datos>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

//Que diseño quieres que te pinte
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.user_item,
            parent, false
        )
        return MyViewHolder(itemView)

    }
    // onbindViewHolder que voy a pintar
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = userList[position]
        holder.firstName.text = currentitem.Zona
        holder.lastName.text = currentitem.Identificador
        holder.age.text = currentitem.Nombre

        holder.itemView.setOnClickListener {
            //implementar interfaz y buscarla como implementarla
        }


    }
    //Tamaño de los datos y pintarte los datos que contenga
    override fun getItemCount(): Int {
        return userList.size

    }
    fun searchDataList(searList: ArrayList<Datos>){
        userList = searList
        notifyDataSetChanged()
    }

    //myviewHolder donde los voy a pintar
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val firstName : TextView = itemView.findViewById(R.id.tvfirstName)
        val lastName : TextView = itemView.findViewById(R.id.tvlastName)
        val age : TextView = itemView.findViewById(R.id.tvage)

    }

}