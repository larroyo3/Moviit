package fr.acyll.moviit.features.publish

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


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
                            shootingPlaceId = it.shootingPlaceId,
                            title = it.shootingPlace?.place ?: ""
                        )
                    )
                }

                uploadImage()
            }

            is PublishEvent.OnAddImage -> {
                _state.update {
                    it.copy(
                        imageUri = event.value
                    )
                }
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
                            shootingPlaceId = document.id
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

    private fun uploadImage() {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("images/${state.value.imageUri?.encodedPath}")
        val uploadTask = imageRef.putFile(state.value.imageUri!!)

        uploadTask.addOnSuccessListener { _ ->
            getUrlFromUploadImage(imageRef)
        }.addOnFailureListener { e ->
            emitEffect(PublishEffect.ShowError(e))
        }
    }

    private fun getUrlFromUploadImage(imageRef: StorageReference) {
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()

            _state.update {
                it.copy(
                    memories = it.memories.copy(
                        imageURL = imageUrl
                    )
                )
            }

            publishMemory()
        }.addOnFailureListener { error ->
            emitEffect(PublishEffect.ShowError(error))
        }
    }

    private fun emitEffect(effect: PublishEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}