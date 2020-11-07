package club.txyminecraft

import club.txyminecraft.mcping.MinecraftPing
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.join


suspend fun main() {
    val mcPing = MinecraftPing()
    val ip1 = "txyminecraft.club:25565"
    val ip2 = "http://txyminecraft.club:8123"
    val ip3 = "https://git-a-live.github.io/item_wiki.github.io/"
    val ip4 = "https://git-a-live.github.io/itas.github.io/"

    println("QQ号：")
    val qqId = readLine()?.toLong()//Bot的QQ号
    println("密码：")
    val password = readLine().toString()//Bot的密码
    val miraiBot = Bot(qqId!!, password).alsoLogin()//新建Bot并登录
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

        (contains("#wiki") or contains("#百科")) {
            reply("物品维基：${ip3}")
        }

        (contains("#faq") or contains("#问题")) {
            reply("常见问题猛戳这里：${ip4}")
        }
    }
    miraiBot.join() // 等待 Bot 离线, 避免主线程退出
}

private fun getServerInfo(mcPing: MinecraftPing?): String {
    val sb = StringBuilder()
    if (mcPing == null) {
        return "暂时无法获取服务器信息"
    } else {
        val mcPR = mcPing.getPing("txyminecraft.club")
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

