package fr.acyll.moviit.features.contribute

import android.net.Uri
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.domain.model.movies.MovieEntity
import fr.acyll.moviit.domain.model.onboarding.UserData

data class ContributeState(
    val isLoading: Boolean = false,
    val shootingPlace: ShootingPlace = ShootingPlace(),
    val userData: UserData? = null,
    val imageUri: Uri? = null,

    val searchResult: List<MovieEntity> = emptyList(),
    val selectedMovie: MovieEntity? = null
)

sealed class ContributeEvent {
    data class OnSelectMovie(val value: MovieEntity): ContributeEvent()
    data class OnLatitudeChange(val value: String): ContributeEvent()
    data class OnLongitudesChange(val value: String): ContributeEvent()
    data class OnPlaceChange(val value: String): ContributeEvent()
    data object OnAddClick: ContributeEvent()
    data class OnSearchQueryChange(val value: String): ContributeEvent()
}

sealed class ContributeEffect {
    data class ShowError(val error: Throwable): ContributeEffect()
    data object NavigateBack: ContributeEffect()
}