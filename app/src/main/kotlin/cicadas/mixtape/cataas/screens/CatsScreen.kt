package cicadas.mixtape.cataas.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonGroup
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cicadas.mixtape.cataas.R
import cicadas.mixtape.cataas.createNewRequest
import cicadas.mixtape.cataas.saveGallery
import coil3.Bitmap
import coil3.compose.SubcomposeAsyncImage
import coil3.toBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CatsScreen(context: Context, modifier: Modifier) {
    val name = stringResource(R.string.app_name)

    var saveState by remember { mutableStateOf(false) }
    var saveBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val saveScope = rememberCoroutineScope()

    var endpoint by remember { mutableStateOf("cat") }
    val request = remember(endpoint) { createNewRequest(context, endpoint) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to $name!",
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )

        SubcomposeAsyncImage(
            model = request,
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .size(350.dp),
            contentScale = ContentScale.Crop,
            loading = {
                Box(contentAlignment = Alignment.Center) {
                    CircularWavyProgressIndicator()
                }
            },
            onLoading = {
                saveState = false
                saveBitmap = null
            },
            // success = {},
            onSuccess = {
                saveState = true
                saveBitmap = it.result.image.toBitmap()
            },
            error = {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error",
                        color = Color.Red,
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                    )
                }
            },
            onError = {
                Log.e("Cataas", "Error: ", it.result.throwable)
            }
        )

        ButtonGroup(
            overflowIndicator = {},
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            expandedRatio = 0f
        ) {
            clickableItem(
                onClick = { endpoint = "cat?t=${System.currentTimeMillis()}" },
                label = "Meow",
            )

            clickableItem(
                onClick = {
                    saveScope.launch(Dispatchers.IO) {
                        val saveBitmap = saveBitmap ?: return@launch
                        saveGallery(context, saveBitmap.asShared())
                    }
                },
                label = "Save",
                enabled = saveState
            )
        }
    }
}