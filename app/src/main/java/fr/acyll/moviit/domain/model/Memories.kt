package fr.acyll.moviit.domain.model

import java.util.Date

data class Memories(
    val title: String = "",
    val description: String = "",
    val creationDate: Date = Date(),
    val imageURL: String = "",

    val author: String = "",
    val authorId: String = "",
    val authorProfilePictureUrl: String = "",

    val shootingPlaceId: String = ""
)
