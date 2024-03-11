package fr.acyll.moviit.features.main.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import fr.acyll.moviit.domain.model.ShootingPlace
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel: ViewModel() {

    private val _state = MutableStateFlow(MapState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MapEffect>()
    val effect = _effect.asSharedFlow()

    init {
        _state.update { it.copy(isLoading = true) }
        getShootingPlaces()
    }

    fun onEvent(event: MapEvent) {
        when (event) {
            is MapEvent.OnMarkerClick -> {
                _state.update {
                    it.copy(
                        showMarkerBottomSheet = true,
                        selectedShootingPlace = event.value
                    )
                }
            }

            is MapEvent.OnDismissMarkerBottomSheet -> {
                _state.update {
                    it.copy(
                        showMarkerBottomSheet = false,
                        selectedShootingPlace = null
                    )
                }
            }

            is MapEvent.OnPublishClick -> {
                emitEffect(MapEffect.GoToPublishPage(event.value))
            }
        }
    }

    private fun emitEffect(effect: MapEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private fun getShootingPlaces() {
        val shootingPlacesCollection = Firebase.firestore.collection("shooting_place")
        shootingPlacesCollection.get()
            .addOnSuccessListener { result ->
                val shootingPlaces: MutableList<ShootingPlace> = mutableListOf()

                for (document in result) {
                    val shootingPlace = document.toObject<ShootingPlace>()
                    shootingPlaces.add(shootingPlace.copy(id = document.id))
                }

                _state.update {
                    it.copy(
                        shootingPlaces = shootingPlaces,
                        isLoading = false
                    )
                }
            }
            .addOnFailureListener { exception ->
                emitEffect(MapEffect.ShowError(exception))
            }
    }
}