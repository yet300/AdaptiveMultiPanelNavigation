package org.yet.adaptive.screen.tab.settings.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.app.settings.SettingType

@Composable
fun SettingItem(
    item: SettingItem,
    select: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (select) MaterialTheme.colorScheme.primary.copy(alpha = 0.4f) else MaterialTheme.colorScheme.surface,
            contentColor = if (select) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconItem(
                    icon = item.icon,
                    title = item.title,
                    tint = if (select) MaterialTheme.colorScheme.onBackground else item.iconTint,
                    backgroundColor = if (select) MaterialTheme.colorScheme.background else item.iconBackgroundColor
                )
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open ${item.title}",
                tint = if (select) Color.Transparent else MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.6f
                )
            )
        }
    }
}

@Composable
fun IconItem(
    icon: ImageVector,
    title: String,
    tint: Color,
    backgroundColor: Color,
) {
    Box(
        modifier = Modifier.size(24.dp).background(backgroundColor, RoundedCornerShape(8.dp)),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = tint,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(4.dp)
        )
    }
}

data class SettingItem(
    val type: SettingType,
    val title: String,

    val icon: ImageVector,
    val iconTint: Color,
    val iconBackgroundColor: Color,

    val onClick: () -> Unit,
)