package com.example.u2taller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EquipoAdapter(private val equipos: List<Equipo>) :
    RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_equipo, parent, false)
        return EquipoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val equipo = equipos[position]
        holder.bind(equipo)
    }

    override fun getItemCount(): Int = equipos.size

    class EquipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nombre: TextView = itemView.findViewById(R.id.nombre)
        private val ciudad: TextView = itemView.findViewById(R.id.ciudad)
        private val estadio: TextView = itemView.findViewById(R.id.estadio)

        fun bind(equipo: Equipo) {
            nombre.text = equipo.nombre
            ciudad.text = equipo.ciudad
            estadio.text = equipo.estadio
        }
    }
}