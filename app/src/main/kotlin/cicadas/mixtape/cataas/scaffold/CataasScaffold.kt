package cicadas.mixtape.cataas.scaffold

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cicadas.mixtape.cataas.navigation.CataasNavHost
import cicadas.mixtape.cataas.navigation.CataasNavigationBar

@Composable
fun CataasScaffold() {
    val navController = rememberNavController()
    val navCurrentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navCurrentBackStackEntry?.destination?.route ?: "cats"

    Scaffold(
        bottomBar = {
            CataasNavigationBar(currentRoute) {
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
        CataasNavHost(navController, Modifier.padding(it))
    }
}