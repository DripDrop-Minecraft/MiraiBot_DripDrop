package club.txyminecraft

import club.txyminecraft.mcping.MinecraftPing
import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.Bot
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.join


suspend fun main() {
    val mcPing = MinecraftPing()
    println("QQ号：")
    val qqId = readLine()?.toLong()//Bot的QQ号
    println("密码：")
    val password = readLine().toString()//Bot的密码
    val miraiBot = Bot(qqId!!, password).alsoLogin()//新建Bot并登录
    miraiBot.subscribeMessages {

        (contains("#ip") or
         contains("#IP") or
         contains("#地址")) {
            reply("服务器IP：txyminecraft.club:25565")
        }

        (contains("#map") or
         contains("#MAP") or
         contains("#地图")) {
            reply("卫星地图：http://txyminecraft.club:8123/index.html"
                    + "\n(推荐使用QueDe Map for Android)")
        }

        (contains("#svr") or
         contains("#SVR") or
         contains("#服务器")) {
            reply(getServerInfo(mcPing))
        }
    }
    miraiBot.join() // 等待 Bot 离线, 避免主线程退出
}

private fun getServerInfo(mcPing: MinecraftPing): String{
    val sb = StringBuilder()
    val mcPR = mcPing.getPing("txyminecraft.club")
    if (mcPing == null){
        return "暂时无法获取服务器信息"
    } else {
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

