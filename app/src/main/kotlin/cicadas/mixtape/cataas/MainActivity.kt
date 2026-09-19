package cicadas.mixtape.cataas

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cicadas.mixtape.cataas.scaffold.Scaffold
import cicadas.mixtape.cataas.ui.theme.CataasTheme
import coil3.ImageLoader
import coil3.SingletonImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.CachePolicy
import coil3.request.ImageRequest

fun createNewRequest(context: Context, endpoint: String) = ImageRequest.Builder(context)
    .data("https://cataas.com/$endpoint")
    .memoryCachePolicy(CachePolicy.DISABLED)
    .diskCachePolicy(CachePolicy.DISABLED)
    .build()

class MainActivity : ComponentActivity() {
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
                Scaffold()
            }
        }
    }
}