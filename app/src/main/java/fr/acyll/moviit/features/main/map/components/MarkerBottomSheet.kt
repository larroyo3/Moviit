package fr.acyll.moviit.features.main.map.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.acyll.moviit.R
import fr.acyll.moviit.components.BottomSheet
import fr.acyll.moviit.components.PrimaryButton
import fr.acyll.moviit.components.buttons.SecondaryButton
import fr.acyll.moviit.domain.model.ShootingPlace

@Composable
fun MarkerBottomSheet(
    onDismiss: () -> Unit,
    onPublishClick: () -> Unit,
    shootingPlace: ShootingPlace
) {
    BottomSheet(
        title = shootingPlace.title,
        onDismiss = onDismiss,
        body = {
               Text(text = "coucou")
        },
        footer = {
            PrimaryButton(
                label = stringResource(id = R.string.publish_memories),
                onClick = {
                    onDismiss()
                    onPublishClick()
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            SecondaryButton(
                label = stringResource(id = R.string.close),
                onClick = onDismiss
            )
        }
    )
}