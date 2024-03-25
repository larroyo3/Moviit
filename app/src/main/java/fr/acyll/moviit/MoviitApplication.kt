package fr.acyll.moviit

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import fr.acyll.moviit.di.appModule
import fr.acyll.moviit.di.coreModule
import fr.acyll.moviit.di.logicModule
import fr.acyll.moviit.di.repositoryModule
import fr.acyll.moviit.languages.Languages
import fr.acyll.moviit.languages.LocaleHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@App)
            modules(coreModule, logicModule, repositoryModule, appModule)
        }
    }
}