package com.nebiozkan.akrepindirici.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.nebiozkan.akrepindirici.domain.model.MediaItem
import com.nebiozkan.akrepindirici.ui.theme.LocalAppColors

/**
 * Grid-based history view shared by the Home, TikTok, and Instagram tabs,
 * matching the spec's "ızgara şeklinde gösterim" (grid display) requirement.
 */
@Composable
fun MediaHistoryGrid(
    items: List<MediaItem>,
    onItemClick: (MediaItem) -> Unit,
    onItemLongPress: (MediaItem) -> Unit,
    modifier: Modifier = Modifier,
    emptyContent: @Composable () -> Unit = {}
) {
    if (items.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            emptyContent()
        }
        return
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items, key = { it.id }) { item ->
            MediaGridCell(
                item = item,
                onClick = { onItemClick(item) },
                onLongPress = { onItemLongPress(item) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MediaGridCell(
    item: MediaItem,
    onClick: () -> Unit,
    onLongPress: () -> Unit
) {
    val colors = LocalAppColors.current
    Box(
        modifier = Modifier
            .padding(4.dp)
            .aspectRatio(0.65f)
            .clip(RoundedCornerShape(12.dp))
            .background(colors.surfaceVariant)
            .combinedClickable(onClick = onClick, onLongClick = onLongPress),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Filled.PlayCircle,
            contentDescription = item.displayName,
            tint = colors.textSecondary.copy(alpha = 0.7f)
        )
        Text(
            text = item.displayName,
            style = MaterialTheme.typography.labelMedium,
            color = colors.textSecondary,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(6.dp)
        )
    }
}
