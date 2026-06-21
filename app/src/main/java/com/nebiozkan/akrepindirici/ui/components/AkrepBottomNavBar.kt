package com.nebiozkan.akrepindirici.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import com.nebiozkan.akrepindirici.ui.navigation.Destination
import com.nebiozkan.akrepindirici.ui.theme.LocalAppColors

@Composable
fun AkrepBottomNavBar(
    currentRoute: String?,
    onNavigate: (Destination) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = LocalAppColors.current
    val items: List<Destination> = Destination.bottomNavItems

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(colors.surface)
            .padding(vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        for (destination in items) {
            val selected = destination.route == currentRoute
            val interactionSource = remember { MutableInteractionSource() }
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) { onNavigate(destination) }
                    .padding(horizontal = 14.dp, vertical = 4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = if (selected) destination.selectedIcon else destination.unselectedIcon,
                    contentDescription = stringResource(destination.labelRes),
                    tint = if (selected) colors.accent else colors.textSecondary,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = stringResource(destination.labelRes),
                    style = MaterialTheme.typography.labelMedium,
                    color = if (selected) colors.accent else colors.textSecondary
                )
            }
        }
    }
}
