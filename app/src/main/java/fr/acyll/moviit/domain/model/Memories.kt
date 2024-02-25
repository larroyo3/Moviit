package fr.acyll.moviit.domain.model

import com.google.firebase.firestore.DocumentReference
import java.util.Date

data class Memories(
    val title: String = "",
    val description: String = "",
    val creationDate: Date = Date(),

    val author: String = "",
    val authorId: String = "",
    val authorProfilePictureUrl: String = "",

    val shootingPlaceId: DocumentReference? = null
)
