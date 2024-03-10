package fr.acyll.moviit.features.contribute

import android.net.Uri
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.domain.model.onboarding.UserData

data class ContributeState(
    val isLoading: Boolean = false,
    val shootingPlace: ShootingPlace = ShootingPlace(),
    val userData: UserData? = null,
    val imageUri: Uri? = null,
)

sealed class ContributeEvent {
    data class OnTitleChange(val value: String): ContributeEvent()
    data class OnDirectorChange(val value: String): ContributeEvent()
    data class OnReleaseDateChange(val value: String): ContributeEvent()
    data class OnSynopsisChange(val value: String): ContributeEvent()
    data class OnLatitudeChange(val value: String): ContributeEvent()
    data class OnLongitudesChange(val value: String): ContributeEvent()
    data object OnAddClick: ContributeEvent()
    data class OnAddImage(val value: Uri): ContributeEvent()
}

sealed class ContributeEffect {
    data class ShowError(val error: Throwable): ContributeEffect()
    data object NavigateBack: ContributeEffect()
}