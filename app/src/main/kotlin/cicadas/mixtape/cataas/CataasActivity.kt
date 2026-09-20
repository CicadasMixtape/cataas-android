package cicadas.mixtape.cataas

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cicadas.mixtape.cataas.scaffold.CataasScaffold
import cicadas.mixtape.cataas.ui.theme.CataasTheme
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun createNewRequest(context: Context, endpoint: String) = ImageRequest.Builder(context)
    .data("https://cataas.com/$endpoint")
    .memoryCachePolicy(CachePolicy.DISABLED)
    .diskCachePolicy(CachePolicy.DISABLED)
    .build()

suspend fun saveGallery(context: Context, bitmap: Bitmap) {
    val content = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "cat-${System.currentTimeMillis()}")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/Cataas")
        put(MediaStore.MediaColumns.IS_PENDING, 1)
    }

    val resolver = context.contentResolver
    val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content) ?: return

    try {
        resolver.openOutputStream(uri).use {
            if (it != null) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
            }
        }

        content.clear()
        content.put(MediaStore.MediaColumns.IS_PENDING, 0)
        resolver.update(uri, content, null, null)

        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Успешно сохранено!", Toast.LENGTH_SHORT).show()
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        Log.e("Cataas", "Error: ", e)
    }
}

class CataasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SingletonImageLoader.setSafe {
            ImageLoader.Builder(it)
                .components { add(OkHttpNetworkFetcherFactory()) }
                .build()
        }

        enableEdgeToEdge()

        setContent {
            CataasTheme {
                CataasScaffold()
            }
        }
    }
}