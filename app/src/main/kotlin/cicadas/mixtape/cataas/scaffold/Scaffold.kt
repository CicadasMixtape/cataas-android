package cicadas.mixtape.cataas.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cicadas.mixtape.cataas.navigation.NavHost
import cicadas.mixtape.cataas.navigation.NavigationBar

@Composable
fun Scaffold() {
    val navController = rememberNavController()
    val navCurrentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navCurrentBackStackEntry?.destination?.route ?: "cats"

    Scaffold(
        bottomBar = {
            NavigationBar(currentRoute) {
                navController.navigate(it) {
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }

                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) {
        NavHost(navController, Modifier.padding(it))
    }
}