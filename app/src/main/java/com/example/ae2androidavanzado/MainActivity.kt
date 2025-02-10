package com.example.ae2androidavanzado
import ApiService
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest



class MainActivity : AppCompatActivity() {


    private lateinit var apiService: ApiService
    val privateKey = "9557274af87f905545c8c81e6f53056a222a8cb1"
    val publicKey = "53693b90ce3218cd5d2ba5ede6391b48"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Retrofit
        apiService = Retrofit.Builder()
            .baseUrl("https://gateway.marvel.com/v1/public/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        // Configuracion de los botones
        findViewById<Button>(R.id.btnBeast).setOnClickListener {
            openHeroDetails("Beast")
        }
        findViewById<Button>(R.id.btnIronMan).setOnClickListener {
            openHeroDetails("Iron Man")
        }
        findViewById<Button>(R.id.btnHulk).setOnClickListener {
            openHeroDetails("Hulk")
        }
        findViewById<Button>(R.id.btnThor).setOnClickListener {
            openHeroDetails("Thor")
        }
        findViewById<Button>(R.id.btnBlackWidow).setOnClickListener {
            openHeroDetails("Black Widow")
        }
        findViewById<Button>(R.id.btnMagneto).setOnClickListener {
            openHeroDetails("Magneto")
        }
        findViewById<Button>(R.id.btnWolverine).setOnClickListener {
            openHeroDetails("Wolverine")
        }
        findViewById<Button>(R.id.btnWStorm).setOnClickListener {
            openHeroDetails("Storm")
        }
    }

    // Para poder usar la API de marvel hay que pasarle 3 parametros, la public key, el private key y el timestamp para poder generar el hashmd5

    private fun openHeroDetails(heroName: String) {
        val ts = System.currentTimeMillis().toString()  // Genera un nuevo timestamp
        val hash = generateMD5Hash(ts, privateKey, publicKey) // Genera el hashmd5 que simplemente concatena los 3 parametros

        // Llamada a la API para obtener los detalles del héroe
        CoroutineScope(Dispatchers.Main).launch {
            val hero = getHeroData(heroName, ts, hash)
            hero?.let {
                // Imprime la URL para verificar que es correcta
                Log.d("MainActivity", "Image URL: ${it.thumbnail.getFullImageUrl()}")

                val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("image_url", it.thumbnail.getFullImageUrl())
                }
                startActivity(intent)
            }
        }
    }

    private suspend fun getHeroData(name: String, ts: String, hash: String): Character? {
        return try {
            val response = apiService.getCharacter(name, ts, publicKey, hash)
            response.data.results.firstOrNull()
        } catch (e: Exception) {
            Log.e("MainActivity", "Error al obtener datos: ${e.message}")
            null
        }
    }
    // este metodo sirve para calcular el hashmd5 que es necesario para acceder a la API de marvel
    fun generateMD5Hash(ts: String, privateKey: String, publicKey: String): String {
        val input = "$ts$privateKey$publicKey"
        val md = MessageDigest.getInstance("MD5")
        val hashBytes = md.digest(input.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }


}



