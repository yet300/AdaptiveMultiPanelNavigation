package org.yet.adaptive.screen.tab.chat.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.app.chat.detail.ChatDetailComponent.Message

@Composable
fun MessageItem(
    message: Message,
    isCurrentUser: Boolean,
    isLargeScreen: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = if (isLargeScreen) 8.dp else 4.dp),
        horizontalArrangement = if (isCurrentUser) Arrangement.spacedBy(
            6.dp,
            Alignment.End
        ) else Arrangement.spacedBy(6.dp, Alignment.Start),
        verticalAlignment = Alignment.Bottom
    ) {
        UserAvatar(
            emoji = message.author.avatar,
            size = 56.dp
        )
        MessageBubble(
            isLargeScreen,
            message,
            isCurrentUser,
        )
    }
}

@Composable
private fun UserAvatar(
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
private fun MessageBubble(
    isLargeScreen: Boolean,
    message: Message,
    isCurrentUser: Boolean,
) {
    val bubbleMaxWidth = if (isLargeScreen) 500.dp else 300.dp
    val bubbleColor =
        if (isCurrentUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant

    val textStyle =
        if (isLargeScreen) MaterialTheme.typography.bodyLarge else MaterialTheme.typography.bodyMedium

    val secondaryTextStyle =
        if (isLargeScreen) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.bodySmall


    Column(
        modifier = Modifier
            .widthIn(max = bubbleMaxWidth)
            .background(
                color = bubbleColor,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(if (isLargeScreen) 12.dp else 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = message.author.name,
                style = textStyle,
                color = if (isCurrentUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = message.time,
                style = secondaryTextStyle,
                color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.8f),
                modifier = Modifier
                    .wrapContentWidth(align = Alignment.End)
                    .widthIn(min = 60.dp)
            )
        }
        Text(
            text = message.text,
            style = textStyle,
            color = if (isCurrentUser) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        )
    }
}