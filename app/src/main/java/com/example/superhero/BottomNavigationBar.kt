import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Info

@Composable
fun BottomNavigationBar(
    selectedScreen: Int,
    onScreenSelected: (Int) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.List, contentDescription = "List") },
            label = { Text("List") },
            selected = selectedScreen == 0,
            onClick = { onScreenSelected(0) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Grid") },
            label = { Text("Grid") },
            selected = selectedScreen == 1,
            onClick = { onScreenSelected(1) }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "About") },
            label = { Text("About") },
            selected = selectedScreen == 2,
            onClick = { onScreenSelected(2) }
        )
    }
}
