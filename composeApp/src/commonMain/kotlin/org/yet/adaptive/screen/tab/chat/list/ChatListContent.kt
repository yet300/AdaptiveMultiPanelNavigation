package org.yet.adaptive.screen.tab.chat.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.chat.list.ChatListComponent
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import org.yet.adaptive.screen.tab.chat.list.component.ChatItem
import org.yet.adaptive.screen.tab.chat.list.component.ChatListTopBar

@Composable
fun ChatListContent(
    component: ChatListComponent,
    modifier: Modifier
) {
    val state by component.model.subscribeAsState()

    val selectionColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)

    Scaffold(
        modifier = modifier,
        topBar = { ChatListTopBar() },
        content = {
            LazyColumn(modifier = modifier.fillMaxSize().padding(it)) {
                items(state.chats) { chat ->
                    val isSelected = chat.id == state.selectedChatId
                    ChatItem(
                        chat = chat,
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = isSelected,
                                onClick = { component.onChatClick(chat.id) }
                            )
                            .run { if (isSelected) background(color = selectionColor) else this }
                            .padding(16.dp)
                    )

                    HorizontalDivider()
                }
            }
        }
    )
}