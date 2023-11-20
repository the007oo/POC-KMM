import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.interop.UIKitView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.ComposeUIViewController
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIView
import platform.UIKit.UIViewController

@OptIn(ExperimentalForeignApi::class)
fun MainViewController() = ComposeUIViewController {
    App()
}

@OptIn(ExperimentalForeignApi::class)
fun ComposeEntryPointWithUIView(
    createUIView: ((String) -> Unit) -> UIView,
    onButtonClick: (String) -> Unit
): UIViewController =
    ComposeUIViewController {
        var text by remember { mutableStateOf("") }
        var messageFromSwift by remember { mutableStateOf("") }

        Column(
            Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .size(300.dp)
                    .border(2.dp, Color.Green),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Kotlin Text filed") }
                )

                Button(onClick = {
                    onButtonClick(text)
                }) {
                    Text("Kotlin Button")
                }

                if(messageFromSwift.isNotEmpty()){
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(text = "Message from swift side: $messageFromSwift")
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            UIKitView(
                modifier = Modifier
                    .size(300.dp)
                    .border(2.dp, Color.Gray),
                factory = { createUIView { value ->
                    messageFromSwift = value
                }
                }
            )
        }
    }
