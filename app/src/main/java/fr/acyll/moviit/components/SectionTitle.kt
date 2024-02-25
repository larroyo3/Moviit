package fr.acyll.moviit.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SectionTitle(
    title: String
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(6.dp))
        HorizontalDivider()
    }

}

@Preview
@Composable
fun PreviewSectionTitle() {
    SectionTitle(
        "Informations"
    )
}