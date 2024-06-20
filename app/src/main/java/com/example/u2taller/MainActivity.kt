package com.example.u2taller

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    private lateinit var equipoAdapter: EquipoAdapter
    private val database = FirebaseDatabase.getInstance()
    private val equiposRef = database.getReference("equipos")
    private val equipos = mutableListOf<Equipo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Obtener referencias a las vistas
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        val editTextNombre: EditText = findViewById(R.id.editTextNombre)
        val editTextCiudad: EditText = findViewById(R.id.editTextCiudad)
        val editTextEstadio: EditText = findViewById(R.id.editTextEstadio)
        val buttonAddEquipo: Button = findViewById(R.id.buttonAddEquipo)

        equipoAdapter = EquipoAdapter(equipos)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = equipoAdapter

        buttonAddEquipo.setOnClickListener {
            val nombre = editTextNombre.text.toString().trim()
            val ciudad = editTextCiudad.text.toString().trim()
            val estadio = editTextEstadio.text.toString().trim()

            if (nombre.isEmpty() || ciudad.isEmpty() || estadio.isEmpty()) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val id = equiposRef.push().key
                if (id != null) {
                    val equipo = Equipo(id, nombre, ciudad, estadio)
                    equiposRef.child(id).setValue(equipo)
                    Toast.makeText(this, "Equipo añadido", Toast.LENGTH_SHORT).show()
                    editTextNombre.text.clear()
                    editTextCiudad.text.clear()
                    editTextEstadio.text.clear()
                }
            }
        }

        fab.setOnClickListener {
            editTextNombre.text.clear()
            editTextCiudad.text.clear()
            editTextEstadio.text.clear()
            Toast.makeText(this, "Formulario limpiado", Toast.LENGTH_SHORT).show()
        }

        equiposRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                equipos.clear()
                for (equipoSnapshot in snapshot.children) {
                    val equipo = equipoSnapshot.getValue(Equipo::class.java)
                    equipo?.let { equipos.add(it) }
                }
                equipoAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}