package dev.shreyash.androidmoviestask.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ErrorMessageWithRetry(
    title: String,
    message: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(text = title,
             style = MaterialTheme.typography.titleMedium,
        )
        if (message != null)
            Text(
                text = message,
                style = MaterialTheme.typography.bodySmall,
            )

        Button(onClick = onRetry) {
            Text(text = "Retry")
        }

    }
}