import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.awt.Desktop
import java.net.URI

@Composable
fun SetOperation(buttonName: MutableState<String>, unlocked: MutableState<Boolean>, login: () -> Unit = {}) {
    Column(
        modifier = Modifier.size(300.dp, 150.dp),
    ) {
        Row(
            modifier = Modifier.size(300.dp, 80.dp).padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp)
                .size(100.dp, 40.dp)
                .weight(1F)
            SetButton(buttonName, modifier, unlocked, login)
        }
        SetBottom()
    }
}

@Composable
private fun SetBottom() {
    Row(
        modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        ClickableText(
            text = AnnotatedString(
                text = "基于Compose Multiplatform以及Mirai项目开发",
                spanStyle = SpanStyle(
                    color = Color(0,0,128),
                    fontSize = 12.sp,
                    textDecoration = TextDecoration.Underline,
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(1F,1F),
                        blurRadius = 5F
                    )
                )
            ),
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Desktop.getDesktop().browse(URI.create("https://github.com/JetBrains/compose-jb"))
            Desktop.getDesktop().browse(URI.create("https://github.com/mamoe/mirai"))
        }
    }
}

@Composable
private fun SetButton(
    buttonName: MutableState<String>,
    modifier: Modifier,
    unlocked: MutableState<Boolean>,
    action: () -> Unit = {}
) {
    Button(
        enabled = unlocked.value,
        onClick = { action() },
        modifier = modifier,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (unlocked.value) {
                Color(	65,105,225)
            } else {
                Color(25 ,25,112)
            },
            contentColor = Color(240,248,255),
            disabledContentColor = Color.Green
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = buttonName.value,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontFamily = FontFamily.SansSerif,
            fontSize = 18F.sp
        )
    }
}