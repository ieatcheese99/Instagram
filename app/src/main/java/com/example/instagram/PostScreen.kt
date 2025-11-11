package com.example.instagram

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.instagram.models.Post
import com.example.instagram.models.samplePosts
import com.example.instagram.ui.components.Story
import com.example.instagram.ui.components.StorySection

// 🔹 Komentar Data Class

@Composable
fun PostScreenContent(modifier: Modifier = Modifier) {

    // ✅ Daftar story dummy
    val stories = listOf(
        Story("Your Story", "https://i.pravatar.cc/150?img=1"),
        Story("anga", "https://i.pravatar.cc/150?img=2"),
        Story("minaa", "https://i.pravatar.cc/150?img=3"),
        Story("rafi", "https://i.pravatar.cc/150?img=4"),
        Story("dapa", "https://i.pravatar.cc/150?img=5"),
        Story("infosuperi", "https://i.pravatar.cc/150?img=6")
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 56.dp)
    ) {
        // ✅ Tambah Story Section di atas
        item {
            StorySection(stories = stories)
            Spacer(modifier = Modifier.height(8.dp))
        }

        // ✅ Postingan di bawah story
        items(samplePosts) { post ->
            PostCard(post = post)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun PostCard(post: Post) {
    var isLiked by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }

    // 🔹 State untuk komentar
    var showComments by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {

        // 🔹 Header (username + profil)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://i.pravatar.cc/150?u=${post.username}",
                contentDescription = "Profile Image",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(post.username, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                if (post.location.isNotEmpty()) {
                    Text(
                        post.location,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            IconButton(onClick = { }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "More")
            }
        }

        // 🔹 Gambar Postingan
        if (post.postImages.isNotEmpty()) {
            AsyncImage(
                model = "https://picsum.photos/400/400?random=${post.username.hashCode()}",
                contentDescription = "Post Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
        }

        // 🔹 Tombol Aksi (Like, Comment, Share, Save)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { isLiked = !isLiked }) {
                Icon(
                    imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = "Like",
                    tint = if (isLiked) Color.Red else MaterialTheme.colorScheme.onSurface
                )
            }

            IconButton(onClick = { showComments = true }) {
                Icon(Icons.Outlined.ChatBubbleOutline, contentDescription = "Comment")
            }

            IconButton(onClick = { /* Share action */ }) {
                Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Share")
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(onClick = { isSaved = !isSaved }) {
                Icon(
                    imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = "Save",
                    tint = if (isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                )
            }
        }

        // 🔹 Like count, caption, dan info
        Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
            Text(
                text = "${post.likes + if (isLiked) 1 else 0} likes",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )

            Text(
                text = "${post.username} ${post.caption}",
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Text(
                text = "View all comments",
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable { showComments = true }
            )

            Text(
                text = "2 hours ago",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                modifier = Modifier.padding(top = 2.dp)
            )
        }

        // 🔹 Buka komentar kalau showComments true
        if (showComments) {
            CommentsSheet(onDismiss = { showComments = false })
        }
    }
}

// ===========================================
// 🔹 KOMENTAR SHEET (ModalBottomSheet Version)
// ===========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsSheet(onDismiss: () -> Unit) {
    var commentText by remember { mutableStateOf("") }
    var comments by remember {
        mutableStateOf(
            listOf(
                CommentData("anga", "Keren banget! 🔥"),
                CommentData("morr", "Suka dengan lokasi ini!"),
                CommentData("zabdan", "Mantap jiwa!"),
                CommentData("travel_buddy", "Boleh tau tempat mana? 📍")
            )
        )
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        tonalElevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .navigationBarsPadding()
        ) {
            Text("Comments", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            ) {
                items(comments) { comment ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        AsyncImage(
                            model = "https://i.pravatar.cc/100?u=${comment.username}",
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Column(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .weight(1f)
                        ) {
                            Text(comment.username, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                            Text(comment.text, fontSize = 13.sp, modifier = Modifier.padding(top = 4.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom
            ) {
                TextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 48.dp)
                        .padding(end = 8.dp),
                    placeholder = { Text("Tambahkan komentar...", fontSize = 13.sp) },
                    shape = CircleShape,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 14.sp),
                    singleLine = false
                )
                IconButton(
                    onClick = {
                        if (commentText.isNotEmpty()) {
                            comments = comments + CommentData("You", commentText)
                            commentText = ""
                        }
                    }
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send", tint = MaterialTheme.colorScheme.primary)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
