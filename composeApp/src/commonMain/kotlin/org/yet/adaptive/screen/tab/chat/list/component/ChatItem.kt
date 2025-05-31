package org.yet.adaptive.screen.tab.chat.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.chat.list.ChatListComponent.Chat


@Composable
fun ChatItem(
    modifier: Modifier = Modifier,
    chat: Chat,
) {
    ChatContent(
        chat = chat,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Composable
private fun ChatContent(
    chat: Chat,
    modifier: Modifier = Modifier
) {
    BoxWithConstraints {
        val isLargeScreen = maxWidth >= 840.dp
        val avatarSize = if (isLargeScreen) 64.dp else 56.dp
        val textStyle =
            if (isLargeScreen) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium
        val secondaryTextStyle =
            if (isLargeScreen) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.bodySmall

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = if (isLargeScreen) 12.dp else 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if (isLargeScreen) 12.dp else 6.dp)
        ) {
            ChatAvatar(
                emoji = chat.avatar,
                size = avatarSize
            )

            ChatTitle(
                chat = chat,
                textStyle = textStyle,
                secondaryTextStyle = secondaryTextStyle
            )
        }
    }
}

@Composable
private fun ChatAvatar(
    emoji: String,
    size: Dp
) {
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
    ) {
        Text(
            text = emoji,
            modifier = Modifier
                .padding(size / 4)
                .align(Alignment.Center),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
private fun ChatTitle(
    chat: Chat,
    textStyle: TextStyle,
    secondaryTextStyle: TextStyle
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = chat.title,
                style = textStyle,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            Text(
                text = chat.time,
                style = secondaryTextStyle,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                modifier = Modifier
                    .wrapContentWidth(align = Alignment.End)
                    .widthIn(min = 60.dp)
            )
        }
        Text(
            text = chat.author.text,
            style = secondaryTextStyle,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f)
        )
    }
}