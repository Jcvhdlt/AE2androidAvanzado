import com.example.ae2androidavanzado.MarvelResponse
import retrofit2.http.GET
import retrofit2.http.Query

// configuramos la interface para poder acceder a la API
interface ApiService {
    @GET("characters")
    suspend fun getCharacter(
        @Query("name") name: String,
        @Query("ts") ts: String,
        @Query("apikey") apiKey: String,
        @Query("hash") hash: String
    ): MarvelResponse
}