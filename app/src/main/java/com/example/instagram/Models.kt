package com.example.instagram.models

// ========================
// DATA CLASSES
// ========================
data class Post(
    val username: String,
    val location: String,
    val postImages: List<String>,
    val caption: String,
    val likes: Int
)

data class Story(
    val username: String,
    val profileImage: String
)

data class Comment(
    val username: String,
    val text: String
)

data class Message(
    val sender: String,
    val text: String,
    val isFromOther: Boolean
)

data class SuggestedUser(
    val username: String,
    val mutualInfo: String,
    val profileImage: String
)

// ========================
// SAMPLE DATA
// ========================
val sampleStories = listOf(
    Story("anga", ""),
    Story("morr", ""),
    Story("zabdan", ""),
    Story("travel_buddy", ""),
    Story("foodie_life", ""),
    Story("tech_guru", "")
)

val samplePosts = listOf(
    Post(
        username = "rosaatyap",
        location = "Pasar Senen, Jakarta",
        postImages = listOf("image1", "image2"),
        caption = "10.00wib Tiba-tiba pulang cepat 😂",
        likes = 352
    ),
    Post(
        username = "morr",
        location = "Bandung",
        postImages = listOf("image1", "image2", "image3"),
        caption = "Ngopi dulu ☕",
        likes = 890
    ),
    Post(
        username = "zabdan",
        location = "Yogyakarta",
        postImages = listOf("image1"),
        caption = "Belajar Jetpack Compose 🚀",
        likes = 567
    )
)

val suggestedUsersList = listOf(
    SuggestedUser("anga", "Diikuti oleh morr + 2 lainnya", "https://i.pravatar.cc/150?img=1"),
    SuggestedUser("morr", "Diikuti oleh zabdan + 3 lainnya", "https://i.pravatar.cc/150?img=2"),
    SuggestedUser("travel_buddy", "Diikuti oleh anga + 1 lainnya", "https://i.pravatar.cc/150?img=3")
)
