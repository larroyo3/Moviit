package fr.acyll.moviit.features.main.account

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.acyll.moviit.domain.model.Memories
import fr.acyll.moviit.domain.model.ShootingPlace
import fr.acyll.moviit.features.main.home.HomeEffect
import fr.acyll.moviit.features.onboarding.auth.GoogleAuthUiClient
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AccountTabsIndex(val index: Int) {
    MEMORIES(0),
    CONTRIBUTION(1)
}
class AccountViewModel(
    context: Context
): ViewModel() {
    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AccountEffect>()
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

    fun onEvent(event: AccountEvent) {
        when (event) {
            is AccountEvent.OnChangeTabs -> {
                _state.update {
                    it.copy(
                        selectedIndex = event.value
                    )
                }
            }
        }
    }

    fun refreshData() {
        _state.update { it.copy(isLoading = true) }
        getMyMemories()
        getMyContribution()
    }

    private fun getMyMemories() {
        val memoriesCollection = Firebase.firestore.collection("memories")

        memoriesCollection.whereEqualTo("authorId", _state.value.userData?.userId).get()
            .addOnSuccessListener { result ->
                val memories: MutableList<Memories> = mutableListOf()
                for (document in result) {
                    memories.add(document.toObject(Memories::class.java))
                }

                _state.update {
                    it.copy(
                        memories = memories.sortedByDescending { date -> date.creationDate },
                        isLoading = false
                    )
                }
            }
            .addOnFailureListener { exception ->
                emitEffect(AccountEffect.ShowError(exception))
            }
    }

    private fun getMyContribution() {
        val shootingPlaceCollection = Firebase.firestore.collection("shooting_place")

        shootingPlaceCollection.whereEqualTo("contributorId", _state.value.userData?.userId).get()
            .addOnSuccessListener { result ->
                val shootingPlace: MutableList<ShootingPlace> = mutableListOf()
                for (document in result) {
                    shootingPlace.add(document.toObject(ShootingPlace::class.java))
                }

                _state.update {
                    it.copy(
                        shootingPlace = shootingPlace,
                        isLoading = false
                    )
                }
            }
            .addOnFailureListener { exception ->
                emitEffect(AccountEffect.ShowError(exception))
            }
    }

    private fun emitEffect(effect: AccountEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}