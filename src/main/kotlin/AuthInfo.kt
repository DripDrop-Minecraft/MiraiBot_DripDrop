import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccountPassword(
    account: String? = null,
    pass: String? = null,
    saveAccount: (String) -> Unit = {},
    savePassword: (String) -> Unit = {}
) {
    Column(
        modifier = Modifier.size(300.dp, 180.dp).padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val modifier = Modifier.padding(10.dp).weight(1F).align(Alignment.Start)
        SetOutlinedTextField(
            value = account ?: "",
            labelName = "请输入QQ账号",
            modifier = modifier,
        ) {
            saveAccount(it)
        }
        SetOutlinedTextField(
            value = pass ?: "",
            labelName = "请输入QQ密码",
            modifier = modifier,
            visualTransformation = PasswordVisualTransformation()
        ) {
            savePassword(it)
        }
    }
}


@Composable
private fun SetOutlinedTextField(
    value: String,
    labelName: String,
    modifier: Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    action: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { action(it) },
        modifier = modifier,
        readOnly = false,
        visualTransformation = visualTransformation,
        label = {
            Text(
                text = labelName,
                color = Color.Red,
                fontSize = (15F).sp,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(vertical = 5.dp)
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            unfocusedBorderColor = Color.Red
        ),
        textStyle = TextStyle(
            color = Color.Black,
            fontSize = (20F).sp
        ),
        singleLine = true,
    )
}