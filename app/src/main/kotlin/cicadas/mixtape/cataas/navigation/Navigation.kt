package cicadas.mixtape.cataas.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Gif
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.TextFields
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cicadas.mixtape.cataas.screens.CatsScreen
import cicadas.mixtape.cataas.screens.GifsScreen
import cicadas.mixtape.cataas.screens.SaysScreen

// Бар навигации
@Composable
fun NavigationBar(currentRoute: String, onSelectRoute: (String) -> Unit) {
    NavigationBar {
        // Навигация на первую страницу (Cats)
        NavigationBarItem(
            currentRoute == "cats",
            onClick = { onSelectRoute("cats") },
            icon = { Icon(Icons.Default.Pets, contentDescription = null) },
            label = { Text("Cats") }
        )

        // Навигация на вторую страницу (Gifs)
        NavigationBarItem(
            currentRoute == "gifs",
            onClick = { onSelectRoute("gifs") },
            icon = { Icon(Icons.Default.Gif, contentDescription = null) },
            label = { Text("Gifs") }
        )

        // Навигация на третью страницу (Says)
        NavigationBarItem(
            currentRoute == "says",
            onClick = { onSelectRoute("says") },
            icon = { Icon(Icons.Default.TextFields, contentDescription = null) },
            label = { Text("Says") }
        )
    }
}

// Хост навигации
// Нужен чтобы менять скрины между друг-другом
@Composable
fun NavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "cats",
        modifier = modifier
    ) {
        composable("cats") {
            CatsScreen(LocalContext.current, modifier)
        }

        composable("gifs") {
            GifsScreen(LocalContext.current, modifier)
        }

        composable("says") {
            SaysScreen(LocalContext.current, modifier)
        }
    }
}