package org.yet.adaptive.screen.tab.chat.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.chat.detail.ChatDetailComponent
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.yet.adaptive.screen.tab.chat.detail.component.MessageAppBar
import org.yet.adaptive.screen.tab.chat.detail.component.MessageInputField
import org.yet.adaptive.screen.tab.chat.detail.component.MessageItem

@Composable
fun MessageDetailContent(
    component: ChatDetailComponent,
    modifier: Modifier = Modifier
) {
    val state by component.model.subscribeAsState()
    var messageText by rememberSaveable { mutableStateOf("") }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isLargeScreen = maxWidth >= 840.dp
        val contentMaxWidth = if (isLargeScreen) 600.dp else maxWidth

        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .then(
                    if (isLargeScreen) Modifier.widthIn(max = contentMaxWidth)
                        .align(Alignment.Center) else Modifier
                ),
            topBar = { MessageAppBar(component, state.isBackButtonVisible) },
            content = { paddingValues ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = if (isLargeScreen) 16.dp else 8.dp),
                    verticalArrangement = Arrangement.spacedBy(if (isLargeScreen) 12.dp else 8.dp)
                ) {
                    items(state.messages) { message ->
                        MessageItem(
                            message = message,
                            isCurrentUser = message.author.id == 1L,
                            isLargeScreen = isLargeScreen
                        )
                    }
                }
            },
            bottomBar = {
                MessageInputField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    onSend = {
                        if (messageText.isNotBlank()) {
                            messageText = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(if (isLargeScreen) 16.dp else 8.dp)
                )
            },
        )
    }
}
