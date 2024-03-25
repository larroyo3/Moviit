package fr.acyll.moviit

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import fr.acyll.moviit.navigation.AppNavigation
import fr.acyll.moviit.ui.theme.MoviitTheme

class MainActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreference = getSharedPreferences("USER_VARIABLES", Context.MODE_PRIVATE)
        when (sharedPreference.getInt("language_selected", Languages.FRANCAIS.ordinal)) {
            Languages.FRANCAIS.ordinal -> LocaleHelper.setLocale(this, "fr")
            Languages.ENGLISH.ordinal -> LocaleHelper.setLocale(this, "en")
            Languages.SPANISH.ordinal -> LocaleHelper.setLocale(this, "es")
            else -> LocaleHelper.setLocale(this, "fr")
        }

        setContent {
            val navController = rememberNavController()

            MoviitTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation(
                        navController = navController,
                        lifecycleScope = lifecycleScope,
                        googleAuthUiClient = googleAuthUiClient
                    )
                }
            }
        }
    }
}