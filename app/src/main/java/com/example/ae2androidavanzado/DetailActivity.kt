package com.example.ae2androidavanzado
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.ae2androidavanzado.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.URL

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)


        val imageUrl = intent.getStringExtra("image_url")

        // Verifica que la URL no sea nula
        if (imageUrl != null) {
            // Mostrar la imagen
            val imageView = findViewById<ImageView>(R.id.heroImage)


            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val url = URL(imageUrl)  // URL de la imagen
                    val inputStream = url.openStream()
                    val bitmap = BitmapFactory.decodeStream(inputStream)


                    launch(Dispatchers.Main) {
                        imageView.setImageBitmap(bitmap)
                    }
                } catch (e: Exception) {
                    launch(Dispatchers.Main) {
                        Log.e("com.example.ae2androidavanzado.DetailActivity", "Error descargando la imagen: ${e.message}")
                    }
                }
            }
        } else {
            Log.e("com.example.ae2androidavanzado.DetailActivity", "URL incorrecta")
        }
    }
}
