package fr.acyll.moviit.features.publish

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import fr.acyll.moviit.domain.model.Memories
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception

class PublishViewModel(
    context: Context
): ViewModel() {
    private val _state = MutableStateFlow(PublishState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PublishEffect>()
    val effect = _effect.asSharedFlow()

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = context,
            oneTapClient = Identity.getSignInClient(context)
        )
    }

    init {
        _state.update {
            it.copy(
                userData = googleAuthUiClient.getSignedInUser()
            )
        }
    }

    fun onEvent(event: PublishEvent) {
        when (event) {
            is PublishEvent.OnTitleChange -> {
                _state.update {
                    it.copy(
                        memories = it.memories.copy(
                            title = event.value
                        )
                    )
                }
            }

            is PublishEvent.OnDescriptionChange -> {
                _state.update {
                    it.copy(
                        memories = it.memories.copy(
                            description = event.value
                        )
                    )
                }
            }

            is PublishEvent.OnPublishClick -> {
                _state.update {
                    it.copy(
                        memories = it.memories.copy(
                            author = it.userData?.username ?: "",
                            authorId = it.userData?.userId ?: "",
                            authorProfilePictureUrl = it.userData?.profilePictureUrl ?: "",
                            shootingPlaceId = it.shootingPlace?.id ?: ""
                        )
                    )
                }

                publishMemory()
            }
        }
    }

    fun getShootingPlaceById(id: String) {
        val shootingPlaceDocumentRef = Firebase.firestore.collection("shooting_place").document(id)

        shootingPlaceDocumentRef.get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val shootingPlace = document.toObject<ShootingPlace>()
                    _state.update {
                        it.copy(
                            shootingPlace = shootingPlace
                        )
                    }

                    _state.update {
                        it.copy(
                            shootingPlace = it.shootingPlace?.copy(id = document.id)
                        )
                    }
                    Log.d("tag", _state.value.shootingPlace.toString())
                } else {
                    emitEffect(PublishEffect.ShowError(Exception("Erreur")))
                }
            }
            .addOnFailureListener { exception ->
                emitEffect(PublishEffect.ShowError(exception))
            }
    }

    private fun publishMemory() {
        val memoriesCollection = Firebase.firestore.collection("memories")

        memoriesCollection.add(_state.value.memories)
            .addOnSuccessListener {
                emitEffect(PublishEffect.NavigateBack)
            }.addOnFailureListener { e ->
                emitEffect(PublishEffect.ShowError(e))
            }
    }

    private fun emitEffect(effect: PublishEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}