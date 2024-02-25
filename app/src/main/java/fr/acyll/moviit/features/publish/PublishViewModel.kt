package fr.acyll.moviit.features.publish

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

class PublishViewModel: ViewModel() {
    private val _state = MutableStateFlow(PublishState())
    val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<PublishEffect>()
    val effect = _effect.asSharedFlow()

    init {
        // TODO : get le movie
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
        }
    }

    private fun emitEffect(effect: PublishEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}