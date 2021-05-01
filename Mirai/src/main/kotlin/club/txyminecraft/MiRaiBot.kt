package club.txyminecraft

import club.txyminecraft.mcping.MinecraftPing
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.join


suspend fun main() {
    val mcPing = MinecraftPing()
    val ip1 = "···"
    val ip2 = "···"
    val ip3 = "···"
    val ip4 = "···"

    print("请输入QQ账号：")
    val account = readLine()?.replace(Regex("\\s"),"")?.toLong()?:123456
    print("请输入QQ密码：")
    val password = readLine()?.replace(Regex("\\s"),"")?:"123456"
    val miraiBot = Bot(account, password).alsoLogin()

    miraiBot.subscribeMessages {

        (contains("#ip") or contains("#地址")) {
            reply("服务器IP：${ip1}")
        }

        (contains("#map") or contains("#地图")) {
            reply("卫星地图：${ip2}\n(推荐使用QueDe Map)")
        }

        (contains("#svr") or contains("#服务器")) {
            reply(getServerInfo(mcPing))
        }

        (contains("#wiki")) {
            reply("服务器Wiki猛戳这里：\n地址一：${ip3}\n地址二：${ip4}")
        }
    }
    miraiBot.join() // 等待 Bot 离线, 避免主线程退出
}

private fun getServerInfo(mcPing: MinecraftPing?): String {
    val sb = StringBuilder()
    if (mcPing == null) {
        return "暂时无法获取服务器信息"
    } else {
        val mcPR = mcPing.getPing("···")
        try {
            runBlocking {
                sb.append("在线玩家：")
                        .append(mcPR!!.getPlayers()!!.online)
                        .append("/").append(mcPR.getPlayers()!!.max)
                        .append("\n")
                        .append("玩家列表：")
                if (mcPR.getPlayers() == null){
                    sb.append("")
                } else {
                    for (player in mcPR.getPlayers()!!.sample!!){
                        sb.append("\n").append(player.name)
                    }
                }
            }
        } catch (e: Exception){
            print(e.message.toString())
        }
        return sb.toString()
    }
}

