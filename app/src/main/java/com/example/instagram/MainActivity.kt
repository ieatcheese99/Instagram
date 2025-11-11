package com.example.instagram

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.instagram.models.Message
import com.example.instagram.models.Story
import com.example.instagram.models.sampleStories
import com.example.instagram.ui.theme.InstagramTheme

// ✅ Import screen lain
import com.example.instagram.PostScreenContent
import com.example.instagram.SearchScreenContent
import com.example.instagram.ProfileScreenContent
import com.example.instagram.ReelsScreen
import com.example.instagram.AddPostScreen

class MainActivity : ComponentActivity() {

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        setContent {
            InstagramTheme {
                InstagramApp()
            }
        }
    }
}

@Composable
fun InstagramApp() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var showDirectMessages by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            when (selectedTab) {
                0 -> InstagramTopBar { showDirectMessages = true }
                4 -> ProfileTopBar()
            }
        },
        bottomBar = {
            InstagramBottomNavigation(
                selectedIndex = selectedTab,
                onItemSelected = { selectedTab = it }
            )
        }
    ) { innerPadding ->

        if (showDirectMessages) {
            DirectMessagesScreen(
                modifier = Modifier.padding(innerPadding),
                onBack = { showDirectMessages = false }
            )
        } else {
            when (selectedTab) {
                0 -> PostScreenContent(Modifier.padding(innerPadding))
                1 -> SearchScreenContent(Modifier.padding(innerPadding))
                2 -> AddPostScreen(Modifier.padding(innerPadding))
                3 -> ReelsScreen(Modifier.padding(innerPadding))
                4 -> ProfileScreenContent(Modifier.padding(innerPadding))
            }
        }
    }
}

@Composable
fun DirectMessagesScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    var messageText by remember { mutableStateOf("") }
    val messages = listOf(
        Message("anga", "Hey! How are you?", true),
        Message("You", "I'm good! How about you?", false),
        Message("anga", "Great! Let's catch up soon", true),
        Message("You", "When are you free?", false)
    )

    Column(modifier = modifier.fillMaxSize()) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
            }
            Text("anga", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
            IconButton(onClick = {}) { Icon(Icons.Default.Call, "Call") }
            IconButton(onClick = {}) { Icon(Icons.Default.Info, "Info") }
        }

        Divider()

        LazyColumn(
            modifier = Modifier.weight(1f).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { msg -> MessageBubble(msg) }
        }

        Divider()

        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f).height(40.dp),
                placeholder = { Text("Message...", fontSize = 14.sp) },
                shape = RoundedCornerShape(20.dp),
                singleLine = true
            )
            IconButton(onClick = { messageText = "" }) {
                Icon(Icons.AutoMirrored.Filled.Send, "Send")
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isFromOther) Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (message.isFromOther) MaterialTheme.colorScheme.surfaceVariant
                    else MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
                .widthIn(max = 250.dp)
        ) {
            Text(message.text, color = if (message.isFromOther) Color.Black else Color.White)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstagramTopBar(onMessagesClick: () -> Unit) {
    TopAppBar(
        title = { Text("Instagram") },
        actions = {
            IconButton(onClick = onMessagesClick) {
                Icon(Icons.Default.Message, null)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar() {
    TopAppBar(title = { Text("Profile") })
}

@Composable
fun InstagramBottomNavigation(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedIndex == 0, onClick = { onItemSelected(0) },
            icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") }
        )
        NavigationBarItem(
            selected = selectedIndex == 1, onClick = { onItemSelected(1) },
            icon = { Icon(Icons.Default.Search, null) }, label = { Text("Search") }
        )
        NavigationBarItem(
            selected = selectedIndex == 2, onClick = { onItemSelected(2) },
            icon = { Icon(Icons.Default.Add, null) }, label = { Text("Add") }
        )
        NavigationBarItem(
            selected = selectedIndex == 3, onClick = { onItemSelected(3) },
            icon = { Icon(Icons.Default.VideoLibrary, null) }, label = { Text("Reels") }
        )
        NavigationBarItem(
            selected = selectedIndex == 4, onClick = { onItemSelected(4) },
            icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") }
        )
    }
}
