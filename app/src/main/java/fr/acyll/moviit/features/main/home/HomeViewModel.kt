package fr.acyll.moviit.features.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import fr.acyll.moviit.domain.model.Memories
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>()
    val effect = _effect.asSharedFlow()

    init {
        _state.update { it.copy(isLoading = true) }
        getPublications()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnMovieTitleChange -> {

            }
        }
    }

    private fun emitEffect(effect: HomeEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }

    private fun getPublications() {
        val publicationCollection = Firebase.firestore.collection("memories")
        publicationCollection.get()
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
                emitEffect(HomeEffect.ShowError(exception))
            }
    }
}