package Adapters

import Controladores.AnuncioControlador
import Controladores.UsuarioControlador
import Entidades.Usuario
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.gymcheck.R
import com.example.gymcheck.databinding.AnuncioLayoutBinding
import com.example.gymcheck.databinding.ProductoLayoutBinding
import com.example.gymcheck.databinding.UsuarioLayoutBinding

class UsuarioAdapter(private var usuarios:List<Usuario>, private var navController: NavController):RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {


    private var editItemClickListener: OnEditItemClickListener? = null



    interface OnEditItemClickListener{
        fun onEditItemClick(usuario: Usuario)
    }

    fun setOnEditClickListener(listener: OnEditItemClickListener?){
        this.editItemClickListener = listener
    }
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
        private var currentUsuario:Usuario? = null
        @SuppressLint("SetTextI18n")
        fun bind(usuario:Usuario){
            currentUsuario = usuario

            val dtranscureed = UsuarioControlador().calcularDiasTranscurridos(usuario.fechaMembresia.toString())
            var dTotal= 0
            var mNombre = " "
            var uid = usuario.cedula
            var bundle: Bundle? = Bundle()
            bundle!!.putString("uid", uid.toString())
            binding.btnMenu.setOnClickListener {
                val popupMenu = PopupMenu(itemView.context, it)

                popupMenu.menuInflater.inflate(R.menu.menu_usuario, popupMenu.menu)
                popupMenu.setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.menu_renovar -> {

//                            currentUsuario?.let {usuario ->
//                            editItemClickListener?.onEditItemClick(usuario)
//
//                            }
                            navController.navigate(R.id.action_buscarFragment_to_asignarUsuarioMembresiaFragment, bundle)
                            true
                        }


                        R.id.menu_eliminar -> {
                            currentUsuario?.idUsuario?.let { id ->
                                UsuarioControlador().eliminarUsuario(usuario)
                                // Notifica al RecyclerView que un elemento ha sido eliminado.
                                notifyItemRemoved(adapterPosition)
                                actualizarLista(UsuarioControlador().mostrarUsuario())
                                notifyDataSetChanged()
                            }


                            true
                        }

                        else -> false
                    }
                }
                popupMenu.show()
            }
            var dias = 0
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
            if(dTotal - dtranscureed <= 0){
                dTotal = 0
            }else {
                dias = dTotal - dtranscureed
            }
            binding.tvMembresia.text = mNombre
            binding.tvCedula.text = usuario.cedula
            binding.tvCaducidad.text = "Quedan $dias dias"
            binding.tvActivo.text = usuario.activo.toString()

        }
    }
}