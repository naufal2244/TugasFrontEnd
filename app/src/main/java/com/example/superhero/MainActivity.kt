package com.example.superhero

import BottomNavigationBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.superhero.model.Hero
import com.example.superhero.model.HeroesRepository
import com.example.superhero.ui.theme.SuperheroesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuperheroesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) { HeroesApp() }

            }
        }
    }
}


@Composable
fun HeroesApp() {
    val navController = rememberNavController()
    val selectedScreen = remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                currentScreen = selectedScreen.value,
                showBackButton = navController.previousBackStackEntry != null,
                onBackClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            BottomNavigationBar(
                selectedScreen = selectedScreen.value,
                onScreenSelected = { selectedIndex ->
                    selectedScreen.value = selectedIndex
                    when (selectedIndex) {
                        0 -> navController.navigate("screen1")
                        1 -> navController.navigate("screen2")
                        2 -> navController.navigate("screen3")
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            NavHost(navController = navController, startDestination = "screen1") {
                composable("screen1") {
                    Screen1 { hero ->
                        navController.navigate("detail/${hero.nameRes}")
                    }
                }
                composable("screen2") {
                    Screen2 { hero ->
                        navController.navigate("detail/${hero.nameRes}")
                    }
                }
                composable("screen3") { Screen3() }
                composable("detail/{heroId}") { backStackEntry ->
                    val heroId = backStackEntry.arguments?.getString("heroId")?.toInt() ?: 0
                    val hero = HeroesRepository.heroes.find { it.nameRes == heroId }
                    hero?.let {
                        DetailScreen(hero = it) {
                            navController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(currentScreen: Int,
              showBackButton: Boolean,
              onBackClick: () -> Unit, modifier: Modifier = Modifier) {
    val title = when (currentScreen) {
        0 -> "List Screen"
        1 -> "Grid Screen"
        2 -> "About Screen"
        else -> "Superhero App"
    }
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.displayLarge
            )
        }, navigationIcon = {
            if (showBackButton) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        },

        modifier = modifier
    )
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SuperheroesTheme {
        HeroesApp()
    }
}
