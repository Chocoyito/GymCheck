package Controladores

import Entidades.Usuario
import okhttp3.*
import java.io.IOException

class UsuarioControlador {
    fun agregarUsuario(usuario:Usuario){
        val urlAPI = "http://192.168.0.15/GymCheck-API/persona/agregar_persona.php"

        val requestBody: RequestBody = FormBody.Builder()
            .add("usuario", usuario.usuario)
            .add("clave", usuario.clave)
            .add("activo", usuario.activo.toString())
            .add("idPersona", usuario.idPersona.toString())
            .build()

        val request: Request = Request.Builder()
            .url(urlAPI)
            .post(requestBody)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val respuesta = response.body?.string()
                    println(respuesta)
                }
                else {
                    println("Error en la respuesta del servidor")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Error en la petición HTTP: ${e.message}")
            }
        })
    }
}