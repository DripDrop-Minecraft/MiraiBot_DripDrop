import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState

@Composable
@Preview
fun ShowErrorDialog(isHidden: MutableState<Boolean>)  {
    Window(
        visible = !isHidden.value,
        title = "错误提示",
        onCloseRequest = { isHidden.value = true },
        resizable = false,
        state = WindowState(
            size = DpSize(300.dp, 100.dp),
            position = WindowPosition(Alignment.Center)
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "请确保QQ账号输入不为空且格式正确！",
                color = Color.Red,
                fontSize = 15.sp
            )
        }
    }
}