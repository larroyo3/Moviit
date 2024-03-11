package fr.acyll.moviit.components.buttons

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.acyll.moviit.R
import fr.acyll.moviit.ui.theme.MoviitTheme

@Composable
fun SecondaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledContainerColor = Color.LightGray,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContentColor = Color.Black
        ),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, color = MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.small)
            .height(45.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview
@Composable
fun SecondaryButtonDP() {
    SecondaryButton(
        label = stringResource(R.string.continuer),
        onClick = {}
    )
}