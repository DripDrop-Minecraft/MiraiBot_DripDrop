// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*

fun main() = launchApplicationWithTray {
    LaunchMirai(it)
}

@Composable
private fun LaunchMirai(it: MutableState<Boolean>) {
    Window(
        visible = it.value,
        title = "Mirai桌面客户端",
        onCloseRequest = { it.value = false },
        resizable = false,
        state = WindowState(
            size = DpSize(300.dp, 350.dp),
            position = WindowPosition(Alignment.Center)
        ),
        icon = painterResource("mirai.png")
    ) {
        ShowWindow(it)
    }
}

private fun launchApplicationWithTray(Launch: @Composable (MutableState<Boolean>) -> Unit) = application {
    val visible = remember { mutableStateOf(true) }
    val trayState = rememberTrayState()
    val notification = rememberNotification("系统提示", "Mirai机器人已退出！")
    Tray(
        state = trayState,
        icon = painterResource("mirai.png"),
        onAction = { visible.value = true }
    ) {
        Item(
            "退出应用",
            onClick = {
                trayState.sendNotification(notification)
                exitApplication()
            }
        )
    }
    Launch(visible)
}

private fun checkQQNumber(account: String, password: String, action: (Boolean) -> Unit) {
    var correct = account.isNotEmpty() && password.isNotEmpty()
    if (correct) {
        for (c in account) {
            if (!"1234567890".contains(c)) {
                correct = false
                break
            }
        }
    }
    if (correct) {
        // TODO: QQ号和密码不为空且QQ号格式正确时，执行后续登录步骤
        action(correct)
    } else {
        println("QQ账号为空或格式不正确！")
    }
}

@Composable
private fun ShowWindow(visible: MutableState<Boolean>) {
    MaterialTheme(
        shapes = Shapes(
            small = RoundedCornerShape(10.dp),
            medium = RoundedCornerShape(10.dp),
            large = RoundedCornerShape(10.dp)
        )
    ) {
        Column(
            modifier = Modifier.size(300.dp, 350.dp).fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            val unlocked = remember { mutableStateOf(true) }
            val right = remember { mutableStateOf(true) }
            val buttonName = remember { mutableStateOf("登录") }
            var account by remember { mutableStateOf("1234567890") }
            var password by remember { mutableStateOf("1234567890") }
            AccountPassword(account, password, {
                account = it
            }) {
                password = it
            }
            SetOperation(buttonName, unlocked) {
                checkQQNumber(account, password) {
                    visible.value = !it
                    right.value = it
                    unlocked.value = !it
                    if (it) {
                        buttonName.value = "已在线"
                    }
                }
            }
            ShowErrorDialog(right)
        }
    }
}