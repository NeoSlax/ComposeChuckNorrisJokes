package ru.eltech.chucknorrisjokes.presetation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.eltech.chucknorrisjokes.di.DaggerAppComponent
import ru.eltech.chucknorrisjokes.presetation.theme.ChuckNorrisJokesTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
    }

    private val component by lazy {
        DaggerAppComponent.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            ChuckNorrisJokesTheme {
                // A surface container using the 'background' color from the theme
                val items = listOf(
                    BottomNavBarItem(
                        "Jokes",
                        Icons.Default.Phone,
                        "jokeList"
                    ),
                    BottomNavBarItem(
                        "Web",
                        Icons.Default.Phone,
                        "webScreen"
                    )
                )

                val navController = rememberNavController()

                Scaffold(bottomBar = {
                    BottomNavBar(items = items, navController = navController, onItemClick = {
                        navController.navigate(it.route) {
                            popUpTo(0)
                        }
                    })
                }) {
                    Navigation(navController = navController, viewModel)
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(
    items: List<BottomNavBarItem>,
    navController: NavController,
    modifier: Modifier = Modifier,
    onItemClick: (BottomNavBarItem) -> Unit
) {

    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        modifier = modifier,
        elevation = 5.dp,

        ) {

        items.forEach { item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = { onItemClick(item) },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        if (selected) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp
                            )
                        } else {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.name
                            )
                        }
                    }
                },
                selectedContentColor = Color.Cyan,
                unselectedContentColor = Color.Gray
            )
        }
    }
}

@Composable
fun Greeting(name: String) {

    Text(text = "Hello $name!")
}

@Composable
fun DefaultPreview() {
    ChuckNorrisJokesTheme {
        Greeting("Android")
    }
}

@Composable
fun Navigation(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "jokeList") {
        composable("jokeList") {
            viewModel.loadJokeList(100)
            JokeScreen(viewModel)
        }
        composable("webScreen") {
            WebScreen()
        }
    }
}

@Composable
fun JokeScreen(viewModel: MainViewModel) {
    val jokes by viewModel.jokeList.observeAsState()
    LazyColumn() {
        jokes?.let {
            for (jokeItem in it) {
                item {
                    Column() {
                        Text(
                            modifier = Modifier.padding(6.dp),
                            text = jokeItem.joke
                        )
                        if (jokeItem.explicit) {
                            Row(
                                horizontalArrangement = Arrangement.End, modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .padding(end = 3.dp, bottom = 3.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(color = Color.Red)
                                        .padding(3.dp)
                                ) {

                                    Text(text = "E", color = Color.White)
                                }

                            }

                        }
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.Gray)
                            .fillMaxWidth()
                            .height(1.dp)
                    ) {

                    }
                }

            }
        }

    }
}

@Composable
fun WebScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "WebScreen")
    }
}