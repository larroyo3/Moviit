package fr.acyll.moviit.features.main.settings

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.acyll.moviit.R
import fr.acyll.moviit.components.NoActionBarScreenContainer
import fr.acyll.moviit.components.PrimaryButton
import fr.acyll.moviit.languages.Languages
import fr.acyll.moviit.languages.LocaleHelper
import fr.acyll.moviit.navigation.BottomNavScreen
import fr.acyll.moviit.navigation.Screen
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    redirectToOnboarding: () -> Unit,
    navigateToScreen: (String) -> Unit
) {
    val state: SettingsState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = Unit) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is SettingsEffect.RedirectToOnboarding -> {
                    redirectToOnboarding()
                }
            }
        }
    }
    NoActionBarScreenContainer {
        ScreenContent(
            state = state,
            onEvent = {
                viewModel.onEvent(it)
            },
            navigateToScreen = {
                navigateToScreen(it)
            }
        )
    }
}

@Composable
fun ScreenContent(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    navigateToScreen: (String) -> Unit
) {
    val context = LocalContext.current
    val sharedPreference = context.getSharedPreferences("USER_VARIABLES", Context.MODE_PRIVATE)
    val editorLanguage = sharedPreference.edit()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.contribute),
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToScreen(Screen.Contribute.route)
                }
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.add_shooting_location),
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null)
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.language),
            style = MaterialTheme.typography.titleMedium
        )
        enumValues<Languages>().forEach { language ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .selectable(
                        selected = (language == state.selectedLanguages),
                        onClick = {
                            editorLanguage?.putInt("language_selected", language.ordinal)?.apply()
                            when (language) {
                                Languages.FRANCAIS -> LocaleHelper.setLocale(context, "fr")
                                Languages.ENGLISH -> LocaleHelper.setLocale(context, "en")
                                Languages.ESPANOL -> LocaleHelper.setLocale(context, "es")
                            }

                            navigateToScreen(BottomNavScreen.Home.route)
                        },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (language == state.selectedLanguages),
                    onClick = null
                )
                Text(
                    text = language.name.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }


        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            label = stringResource(id = R.string.log_out),
            onClick = { onEvent(SettingsEvent.OnLogOutClick) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsDefaultPreview() {
    ScreenContent(
        state = SettingsState(),
        onEvent = {},
        navigateToScreen = {}
    )
}