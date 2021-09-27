# MiraiBot

A Kotlin console project for Minecraft server DripDrop, based on the open project mirai (High-performance bot framework
for Tencent QQ: https://github.com/mamoe/mirai)

This project can be compiled to a jar and run by commands. Users enter their server port, QQ accounts and passwords (
plaintext), then press Enter and the program will connect to Tencent's servers and tries to log in.

The bot replies automatically to anyone who sends specific messages on QQ, no matter these messages are sent to her
directly, or just sent in a QQ group. For example, if someone wants to know what the IP address of your Minecraft server
is, just sends "IP" (or any other command provided by you) to her or in a QQ group, she will reply the IP address such
as "minecraft.com:1234".

Of course, if developers need more features, it is better to study
the [original project](https://github.com/mamoe/mirai) instead of just forking and modifying this project.


