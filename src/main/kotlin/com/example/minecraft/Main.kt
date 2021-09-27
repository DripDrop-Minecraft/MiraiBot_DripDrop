package com.example.minecraft

import kotlinx.coroutines.runBlocking
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.alsoLogin
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.message.data.MessageSource.Key.quote
import net.mamoe.mirai.message.data.content
import net.mamoe.mirai.utils.BotConfiguration.MiraiProtocol.ANDROID_PAD

fun main(args: Array<String>): Unit = runBlocking {
    val mcPing = MinecraftPing()
    print("设置主机端口号：")
    val port = readLine()?.replace(Regex("\\s"), "")?.toInt() ?: 25565
    val addr = "your_host_name:$port"
    print("请输入QQ账号：")
    val account = readLine()?.replace(Regex("\\s"), "")?.toLong() ?: 12345600000
    print("请输入QQ密码：")
    val password = readLine()?.replace(Regex("\\s"), "") ?: "123456"
    BotFactory.newBot(account, password) {
        fileBasedDeviceInfo() // 使用 device.json 存储设备信息
        protocol = ANDROID_PAD // 使用Android平板协议
    }.alsoLogin().eventChannel.apply {
        subscribeAlways<FriendMessageEvent> {
            message.apply {
                content.apply {
                    subject.apply {
                        when {
                            contains("服务器地址") -> sendMessage(quote() + addr)
                            contains("在线人数") -> sendMessage(quote() + getServerInfo(mcPing, port))
                        }
                    }
                }
            }
        }
    }
}

private fun getServerInfo(mcPing: MinecraftPing?, prt: Int): String {
    val sb = StringBuilder()
    mcPing?.let {
        val host = "your_host_name"
        val mcPR = it.getPing(host, prt)
        mcPR?.let {
            try {
                runBlocking {
                    mcPR.getPlayers()?.let {
                        sb.append("在线玩家：")
                            .append(mcPR.getPlayers()!!.online)
                            .append("/").append(mcPR.getPlayers()!!.max)
                            .append("\n")
                            .append("玩家列表：")
                        if (mcPR.getPlayers()!!.sample != null) {
                            for (player in mcPR.getPlayers()!!.sample!!) {
                                sb.append("\n").append(player.name)
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                print(e.message.toString())
            }
        }
    }
    return sb.toString()
}