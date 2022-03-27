package ru.eltech.chucknorrisjokes.presetation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ru.eltech.chucknorrisjokes.R
import ru.eltech.chucknorrisjokes.di.DaggerAppComponent
import ru.eltech.chucknorrisjokes.domain.entities.JokeEntity
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
        viewModel.jokeList
        setContent {
            ChuckNorrisJokesTheme {

                // A surface container using the 'background' color from the theme
                val items = listOf(
                    BottomNavBarItem(
                        getString(R.string.jokes),
                        ImageVector.vectorResource(id = R.drawable.ic_joke),
                        "jokeList"
                    ),
                    BottomNavBarItem(
                        getString(R.string.web),
                        ImageVector.vectorResource(id = R.drawable.ic_web),
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
                }) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        Navigation(navController = navController, viewModel)
                    }
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
fun Navigation(navController: NavHostController, viewModel: MainViewModel) {
    NavHost(navController = navController, startDestination = "jokeList") {
        composable("jokeList") {

            JokeScreen(viewModel)
        }
        composable("webScreen") {
            WebScreen()
        }
    }
}

@Composable
fun JokeScreen(viewModel: MainViewModel) {

    val jokes = viewModel.jokeList.observeAsState().value
    if (jokes == null) {
        LoadScreen(viewModel)
    } else {
        JokeColumn(jokes = jokes)
    }


}

@Composable
fun LoadScreen(viewModel: MainViewModel) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val number = remember {
            mutableStateOf("1")
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = number.value,
                label = {
                    Text(text = "Count")
                },
                onValueChange = {
                    number.value = it
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Button(onClick = { viewModel.loadJokeList(number.value.toInt()) }) {
                Text(text = "RELOAD")

            }
        }

    }
}

@Composable
fun JokeColumn(jokes: List<JokeEntity>) {
    LazyColumn() {

        for (jokeItem in jokes) {
            item {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min)
                ) {
                    Text(
                        modifier = Modifier
                            .weight(0.95f)
                            .padding(6.dp),
                        text = jokeItem.joke
                    )

                    if (jokeItem.explicit) {

                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(
                                    Color.Red
                                ), contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "E", color = Color.White, modifier = Modifier
                                    .padding(6.dp)

                            )
                        }
                    }
                }
                Divider(
                    color = Color.Gray,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                )
            }

        }


    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebScreen() {
    val backEnabled = remember { mutableStateOf(false) }
    val urlState = rememberSaveable {
        mutableStateOf("https://www.icndb.com/api/")
    }
    var webView: WebView? = null

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView, url: String?, favicon: Bitmap?) {
                        backEnabled.value = view.canGoBack()
                        urlState.value = url ?: "https://www.icndb.com/api/"
                    }

                }
                settings.javaScriptEnabled = true

                loadUrl(urlState.value)

            }
        }, update = {
            webView = it
        })
    BackHandler(enabled = backEnabled.value) {
        webView?.goBack()
    }

}