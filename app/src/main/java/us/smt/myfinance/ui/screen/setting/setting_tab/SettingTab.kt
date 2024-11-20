package us.smt.myfinance.ui.screen.setting.setting_tab

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.hilt.getViewModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

object SettingTab : Tab {
    private fun readResolve(): Any = SettingTab
    override val options: TabOptions
        @Composable
        get() = TabOptions(
            index = 3u,
            title = "Profile",
            icon = rememberVectorPainter(Icons.Default.Person)
        )

    @Composable
    override fun Content() {
        val viewModel = getViewModel<SettingViewModel>()
        val state by viewModel.state.collectAsState()
        ProfileScreen(
            state = state, onAction = viewModel::onAction
        )
    }
}


@Composable
private fun ProfileScreen(
    state: SettingState,
    onAction: (SettingIntent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Ochiq kul rang fon
            .padding(top = 80.dp, bottom = 16.dp)
            .verticalScroll(rememberScrollState()), // Sahifani skroll qilish imkoniyati
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(120.dp)
                .background(Color.Gray, CircleShape)
                .padding(2.dp)
                .background(Color(0xFF6200EE), CircleShape) // Profil tasvir atrofida ko'k rang
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${state.name} ${state.surname}".ifBlank { "No Name" },
            fontSize = 28.sp, // Matnni kattalashtirdik
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE) // Ko'k rang
        )

        Spacer(modifier = Modifier.height(24.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White)
                .padding(16.dp)
                .background(Color.White, RoundedCornerShape(8.dp)), // Rounded corners for the card
            horizontalAlignment = Alignment.Start
        ) {
            ActionItem(text = "Language", onClick = { onAction(SettingIntent.OpenLanguage) })
            HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
            ActionItem(text = "About", onClick = { onAction(SettingIntent.OpenAbout) })
            HorizontalDivider(thickness = 0.5.dp, color = Color.Gray)
            ActionItem(text = "Help", onClick = { onAction(SettingIntent.OpenHelp) })
        }
    }
}

@Composable
fun ActionItem(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick, interactionSource = remember { MutableInteractionSource() }, indication = ripple())
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontSize = 18.sp, // Matnni kattalashtirdik
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = "Next",
            tint = Color(0xFF6200EE) // Ikon rangini ko'k qildik
        )
    }
}


@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        state = SettingState(
            name = "John",
            surname = "Doe"
        ),
        onAction = {}
    )
}

