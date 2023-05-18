package com.theblackbit.blog.interop.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.themeadapter.material3.Mdc3Theme

@Composable
fun LoadingButtonComposable(
    modifier: Modifier = Modifier,
    title: String,
    onButtonClick: () -> Unit,
    isLoading: State<Boolean>,
) {
    Button(
        modifier = modifier,
        enabled = !isLoading.value,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = MaterialTheme.colorScheme.primary,
        ),
        onClick = {
            if (!isLoading.value) {
                onButtonClick.invoke()
            }
        },
    ) {
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary,
            )
        } else {
            Text(text = title)
        }
    }
}

@Preview
@Composable
fun PrimaryButtonComposablePreview() {
    val isLoading = remember {
        mutableStateOf(false)
    }
    Mdc3Theme {
        LoadingButtonComposable(
            modifier = Modifier.fillMaxWidth(),
            title = "Login",
            isLoading = isLoading,
            onButtonClick = {},
        )
    }
}
