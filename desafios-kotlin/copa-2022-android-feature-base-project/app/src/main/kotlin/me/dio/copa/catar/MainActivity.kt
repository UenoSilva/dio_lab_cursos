package me.dio.copa.catar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import me.dio.copa.catar.extensions.observe
import me.dio.copa.catar.features.MainScreen
import me.dio.copa.catar.features.MainUiAction
import me.dio.copa.catar.features.MainViewModel
import me.dio.copa.catar.notification.scheduler.extensions.showNotification
import me.dio.copa.catar.ui.theme.Copa2022Theme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            Copa2022Theme {
                val state by viewModel.state.collectAsState()
                MainScreen(matches = state.matches, viewModel::toggleNotification)
            }
        }
    }

    private fun observeActions() {
        viewModel.action.observe(this) { action ->
            when (action) {
                is MainUiAction.MatchesNotFound -> showNotification("", "")
                MainUiAction.Unexpected -> showNotification("", "")

                is MainUiAction.DisableNotification ->
                    NotificationMatcherWorker.cancel(applicationContext, action.match)

                is MainUiAction.EnableNotification ->
                    NotificationMatcherWorker.start(applicationContext, action.match)
            }
        }
    }

}
