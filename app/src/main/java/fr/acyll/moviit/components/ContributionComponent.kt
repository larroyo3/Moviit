package fr.acyll.moviit.components

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import fr.acyll.moviit.domain.model.ShootingPlace

@Composable
fun ContributionComponent(
    shootingPlace: ShootingPlace,
    context: Context,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {

    }
}

@Preview
@Composable
fun ContributionPreview() {
    ContributionComponent(
        context = LocalContext.current,
        shootingPlace = ShootingPlace()
    )
}