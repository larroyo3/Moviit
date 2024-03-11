package fr.acyll.moviit.features.contribute

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fr.acyll.moviit.features.main.settings.SettingsEffect
import fr.acyll.moviit.features.main.settings.SettingsEvent
import fr.acyll.moviit.features.main.settings.SettingsState
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import fr.acyll.moviit.features.publish.PublishEffect
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContributeViewModel: ViewModel() {

    private val _state = MutableStateFlow(ContributeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ContributeEffect>()
    val effect = _effect.asSharedFlow()

    fun onEvent(event: ContributeEvent) {
        when (event) {
            is ContributeEvent.OnTitleChange -> {
                _state.update {
                    it.copy(
                        shootingPlace = it.shootingPlace.copy(
                            title = event.value
                        )
                    )
                }
            }

            is ContributeEvent.OnDirectorChange -> {
                _state.update {
                    it.copy(
                        shootingPlace = it.shootingPlace.copy(
                            director = event.value
                        )
                    )
                }
            }

            is ContributeEvent.OnReleaseDateChange -> {
                _state.update {
                    it.copy(
                        shootingPlace = it.shootingPlace.copy(
                            releaseDate = event.value
                        )
                    )
                }
            }

            is ContributeEvent.OnSynopsisChange -> {
                _state.update {
                    it.copy(
                        shootingPlace = it.shootingPlace.copy(
                            synopsis = event.value
                        )
                    )
                }
            }

            is ContributeEvent.OnLatitudeChange -> {
                _state.update {
                    it.copy(
                        shootingPlace = it.shootingPlace.copy(
                            latitude = event.value
                        )
                    )
                }
            }

            is ContributeEvent.OnLongitudesChange -> {
                _state.update {
                    it.copy(
                        shootingPlace = it.shootingPlace.copy(
                            longitude = event.value
                        )
                    )
                }
            }

            is ContributeEvent.OnAddImage -> {
                _state.update {
                    it.copy(
                        imageUri = event.value
                    )
                }
            }

            is ContributeEvent.OnAddClick -> {
                uploadImage()
            }
        }
    }

    private fun uploadImage() {
        _state.update { it.copy(isLoading = true) }
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/${state.value.imageUri?.encodedPath}")
        val uploadTask = imageRef.putFile(state.value.imageUri!!)

        uploadTask.addOnSuccessListener { _ ->
            getUrlFromUploadImage(imageRef)
        }.addOnFailureListener { e ->
            emitEffect(ContributeEffect.ShowError(e))
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun getUrlFromUploadImage(imageRef: StorageReference) {
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()

            _state.update {
                it.copy(
                    shootingPlace = it.shootingPlace.copy(
                        moviePosterUrl = imageUrl
                    )
                )
            }

            publishMemory()
        }.addOnFailureListener { error ->
            emitEffect(ContributeEffect.ShowError(error))
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun emitEffect(effect: ContributeEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private fun publishMemory() {
        val memoriesCollection = Firebase.firestore.collection("shooting_place")

        memoriesCollection.add(_state.value.shootingPlace)
            .addOnSuccessListener {
                _state.update { it.copy(isLoading = false) }
                emitEffect(ContributeEffect.NavigateBack)
            }.addOnFailureListener { e ->
                _state.update { it.copy(isLoading = false) }
                emitEffect(ContributeEffect.ShowError(e))
            }
    }
}