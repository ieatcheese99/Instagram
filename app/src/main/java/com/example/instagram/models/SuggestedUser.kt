package com.example.instagram.models

data class SuggestedUser(
    val username: String,
    val mutualInfo: String,
    val profileImage: String
)

val suggestedUsersList = listOf(
    SuggestedUser(
        username = "yuki",
        mutualInfo = "Diikuti oleh rafi + 2 lainnya",
        profileImage = "https://i.pravatar.cc/150?img=9"
    ),
    SuggestedUser(
        username = "hana",
        mutualInfo = "Diikuti oleh minaa",
        profileImage = "https://i.pravatar.cc/150?img=10"
    ),
    SuggestedUser(
        username = "aiko",
        mutualInfo = "Diikuti oleh dapa",
        profileImage = "https://i.pravatar.cc/150?img=11"
    ),
    SuggestedUser(
        username = "kevin",
        mutualInfo = "Teman Anda di Facebook",
        profileImage = "https://i.pravatar.cc/150?img=12"
    )
)
