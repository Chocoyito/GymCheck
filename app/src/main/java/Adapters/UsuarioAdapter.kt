package Adapters

import Controladores.UsuarioControlador
import Entidades.Usuario
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gymcheck.databinding.AnuncioLayoutBinding
import com.example.gymcheck.databinding.ProductoLayoutBinding
import com.example.gymcheck.databinding.UsuarioLayoutBinding

class UsuarioAdapter(private var usuarios:List<Usuario>):RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsuarioAdapter.UsuarioViewHolder {
        val binding = UsuarioLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioAdapter.UsuarioViewHolder, position: Int) {
        holder.bind(usuarios[position])
    }

    override fun getItemCount(): Int {
        return usuarios.size
    }
    fun actualizarLista(nuevaLista: List<Usuario>) {
        usuarios = nuevaLista
    }
    inner class UsuarioViewHolder(private val binding: UsuarioLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(usuario:Usuario){

            val dtranscureed = UsuarioControlador().calcularDiasTranscurridos(usuario.fechaMembresia.toString())
            var dTotal= 0
            var mNombre = " "

            binding.tvNombre.text = usuario.usuario
            if(usuario.idMembresia == 1){
                mNombre = "Semanal"
                dTotal = 7
            } else if(usuario.idMembresia == 2){
                mNombre = "Quincenal"
                dTotal = 15
            } else if(usuario.idMembresia == 3){
                mNombre = "Mensual"
                dTotal = 30
            }
            binding.tvMembresia.text = mNombre
            binding.tvCedula.text = usuario.cedula
            binding.tvCaducidad.text = "Quedan ${dTotal - dtranscureed}"
            binding.tvActivo.text = usuario.activo.toString()
        }
    }
}