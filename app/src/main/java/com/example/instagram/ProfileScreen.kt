package com.example.instagram

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.instagram.models.SuggestedUser
import com.example.instagram.models.suggestedUsersList

@Composable
fun ProfileScreenContent(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item { ProfileHeaderSection() }
        item { ProfileBioSection() }
        item { ProfileActionButtons() }
        item { ProfileSuggestedUsers() }
        item { ProfileTabsSection() }
        item { ProfilePostsGridSection() }
    }
}

@Composable
fun ProfileHeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://i.pravatar.cc/150?img=7",
            contentDescription = "Profile",
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(20.dp))

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            ProfileStatItem("5", "Postingan")
            ProfileStatItem("68", "Pengikut")
            ProfileStatItem("106", "Mengikuti")
        }
    }
}

@Composable
fun ProfileStatItem(count: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(count, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Text(
            label,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun ProfileBioSection() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text("Zabdan Harjati Purnomo", fontWeight = FontWeight.Bold)
        Text("Apa kostum Anda?", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
    }
}

@Composable
fun ProfileActionButtons() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ActionButton("Edit profil", Modifier.weight(1f))
        ActionButton("Bagikan profil", Modifier.weight(1f))

        IconButton(
            onClick = {},
            modifier = Modifier.size(36.dp)
        ) {
            Icon(Icons.Default.PersonAdd, contentDescription = null)
        }
    }
}

@Composable
fun ActionButton(text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = {},
        modifier = modifier.height(36.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Text(text, color = MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
fun ProfileSuggestedUsers() {
    Column(Modifier.padding(16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Temukan orang", fontWeight = FontWeight.Medium, fontSize = 16.sp)
            Text("Lihat semua", color = MaterialTheme.colorScheme.primary)
        }

        Spacer(Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(suggestedUsersList) { user ->
                SuggestedUserCardProfile(user)
            }
        }
    }
}

@Composable
fun SuggestedUserCardProfile(user: SuggestedUser) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(200.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(
                    model = user.profileImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(Modifier.height(8.dp))
                Text(user.username, fontWeight = FontWeight.Bold)
                Text(
                    user.mutualInfo,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Ikuti")
            }
        }
    }
}

@Composable
fun ProfileTabsSection() {
    Column {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = {}) { Icon(Icons.Default.GridOn, null) }
            IconButton(onClick = {}) { Icon(Icons.Default.BookmarkBorder, null) }
            IconButton(onClick = {}) { Icon(Icons.Default.Person, null) }
        }
        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
    }
}

@Composable
fun ProfilePostsGridSection() {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(1.dp),
        modifier = Modifier.height(400.dp)
    ) {
        items((1..12).toList()) { index ->
            AsyncImage(
                model = "https://picsum.photos/200/200?random=$index",
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(1.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}
