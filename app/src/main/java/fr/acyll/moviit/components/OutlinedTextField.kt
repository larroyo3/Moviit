package fr.acyll.moviit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String = "",
    placeHolder: String = "",
    onValueChange: ((String) -> Unit)? = null,
    isError: Boolean = false,
    singleLine: Boolean = true,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardAction: ImeAction = ImeAction.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    capitalization: KeyboardCapitalization = KeyboardCapitalization.Sentences,
    supportingText: String = ""
) {
    val textFieldModifier: Modifier = if (singleLine) {
        Modifier
            .fillMaxWidth()
            .height(45.dp)
    } else {
        Modifier
            .fillMaxWidth()
            .height(100.dp)
    }

    Column(
        horizontalAlignment = Alignment.Start
    ) {
        if (label.isNotBlank()) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
        }
        Box(
            modifier = modifier
                .then(textFieldModifier)
                .clickable {
                    onClick?.invoke()
                },
        ) {
            BasicTextField(
                value = value,
                onValueChange = {
                    if (onValueChange != null) {
                        onValueChange(it)
                    }
                },
                modifier = textFieldModifier
                    .padding(0.dp)
                    .fillMaxWidth(),
                enabled = enabled,
                readOnly = readOnly,
                textStyle = MaterialTheme.typography.bodyMedium,
                keyboardOptions = KeyboardOptions(
                    keyboardType = keyboardType,
                    capitalization = capitalization,
                    imeAction = keyboardAction
                ),
                cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
                singleLine = singleLine,
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer,
                                shape = MaterialTheme.shapes.small
                            )
                            .border(
                                width = 2.dp,
                                color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.secondary,
                                shape = MaterialTheme.shapes.small
                            )
                            .padding(all = 12.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        if (leadingIcon != null) {
                            Box(modifier = Modifier.size(24.dp)) {
                                leadingIcon()
                            }
                            Spacer(modifier = Modifier.width(width = 8.dp))
                        }
                        Box(modifier = Modifier.weight(1f)) {
                            innerTextField()
                            if(placeHolder.isNotEmpty() && value.isEmpty()) {
                                Text(
                                    text = placeHolder,
                                    style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        if (trailingIcon != null) {
                            trailingIcon()
                        }
                    }
                }
            )
        }
        if (isError && supportingText.isNotBlank()) {
            Text(
                text = supportingText,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryTextFieldMandatoryPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            label = "titre",
            keyboardType = KeyboardType.Email,
            value = "test@gmail.com",
            onValueChange = {

            },
        )
    }
}
