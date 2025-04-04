package com.hderbali.post.views

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.hderbali.ui.theme.AppDimensions
import com.hderbali.ui.theme.AppSpecificShapes

@Composable
fun NewCommentInput(
    commentText: String,
    isSubmitting: Boolean,
    onCommentTextChanged: (String) -> Unit,
    onSubmitComment: () -> Unit
) {
    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .padding(horizontal = AppDimensions.spacing16, vertical = AppDimensions.spacing8),
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        OutlinedTextField(
            value = commentText,
            onValueChange = onCommentTextChanged,
            modifier = Modifier.Companion.weight(1f),
            placeholder = { Text("Add comment...") },
            maxLines = 3,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Companion.Send),
            keyboardActions = KeyboardActions(onSend = { onSubmitComment() }),
            trailingIcon = {
                if (commentText.isNotEmpty()) {
                    IconButton(
                        onClick = onSubmitComment,
                        enabled = !isSubmitting
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                modifier = Modifier.Companion.size(24.dp),
                                strokeWidth = 2.dp
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.Send,
                                contentDescription = "Envoyer",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            },
            shape = AppSpecificShapes.TextFieldShape
        )
    }
}