package fr.acyll.moviit.languages

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale

object LocaleHelper {
    private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    fun onCreate(context: Context) {
        val lang: String? =
            if (getLanguage(context)!!.isEmpty()) {
                getPersistedData(context, Locale.getDefault().language)
            } else {
                getLanguage(context)
            }
        setLocale(context, lang)
    }

    fun onCreate(
        context: Context,
        defaultLanguage: String
    ) {
        val lang = getPersistedData(context, defaultLanguage)
        setLocale(context, lang)
    }

    fun getLanguage(context: Context): String? {
        return getPersistedData(context, Locale.getDefault().language)
    }

    fun setLocale(
        context: Context,
        language: String?
    ) {
        persist(context, language)
        updateResources(context, language)
    }

    private fun getPersistedData(
        context: Context,
        defaultLanguage: String
    ): String? {
        val preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
    }

    private fun persist(
        context: Context,
        language: String?
    ) {
        val preferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    private fun updateResources(
        context: Context,
        language: String?
    ) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}