package club.txyminecraft.mcping

import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.nio.charset.Charset

class MinecraftPing {
    val minecraftPingUtil = MinecraftPingUtil()
    /**
     * Fetches a [MinecraftPingReply] for the supplied hostname.
     * **Assumed timeout of 2s and port of 25565.**
     *
     * @param hostname - a valid String hostname
     * @return [MinecraftPingReply]
     * @throws IOException
     */
    fun getPing(hostname: String): MinecraftPingReply? {
        return this.getPing(MinecraftPingOptions().setHostname(hostname))
    }

    /**
     * Fetches a [MinecraftPingReply] for the supplied options.
     *
     * @param options - a filled instance of [MinecraftPingOptions]
     * @return [MinecraftPingReply]
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getPing(options: MinecraftPingOptions?): MinecraftPingReply? {
        minecraftPingUtil.validate(options!!.getHostname(), "Hostname cannot be null.")
        minecraftPingUtil.validate(options.getPort(), "Port cannot be null.")
        val socket = Socket()
        socket.connect(InetSocketAddress(options.getHostname(), options.getPort()), options.getTimeout())
        val dis = DataInputStream(socket.getInputStream())
        val out = DataOutputStream(socket.getOutputStream())

        //> Handshake
        val handshake_bytes = ByteArrayOutputStream()
        val handshake = DataOutputStream(handshake_bytes)
        handshake.writeByte(minecraftPingUtil.PACKET_HANDSHAKE.toInt())
        minecraftPingUtil.writeVarInt(handshake, minecraftPingUtil.PROTOCOL_VERSION)
        minecraftPingUtil.writeVarInt(handshake, options.getHostname()!!.length)
        handshake.writeBytes(options.getHostname())
        handshake.writeShort(options.getPort())
        minecraftPingUtil.writeVarInt(handshake, minecraftPingUtil.STATUS_HANDSHAKE)
        minecraftPingUtil.writeVarInt(out, handshake_bytes.size())
        out.write(handshake_bytes.toByteArray())

        //> Status request
        out.writeByte(0x01) // Size of packet
        out.writeByte(minecraftPingUtil.PACKET_STATUSREQUEST.toInt())

        //< Status response
        minecraftPingUtil.readVarInt(dis) // Size
        var id: Int = minecraftPingUtil.readVarInt(dis)
        minecraftPingUtil.io(id == -1, "Server prematurely ended stream.")
        minecraftPingUtil.io(id != minecraftPingUtil.PACKET_STATUSREQUEST.toInt(), "Server returned invalid packet.")
        val length: Int = minecraftPingUtil.readVarInt(dis)
        minecraftPingUtil.io(length == -1, "Server prematurely ended stream.")
        minecraftPingUtil.io(length == 0, "Server returned unexpected value.")
        val data = ByteArray(length)
        dis.readFully(data)
        val json = String(data, Charset.forName(options.getCharset()))

        //> Ping
        out.writeByte(0x09) // Size of packet
        out.writeByte(minecraftPingUtil.PACKET_PING.toInt())
        out.writeLong(System.currentTimeMillis())

        //< Ping
        minecraftPingUtil.readVarInt(dis) // Size
        id = minecraftPingUtil.readVarInt(dis)
        minecraftPingUtil.io(id == -1, "Server prematurely ended stream.")
        minecraftPingUtil.io(id != minecraftPingUtil.PACKET_PING.toInt(), "Server returned invalid packet.")

        // Close
        handshake.close()
        handshake_bytes.close()
        out.close()
        dis.close()
        socket.close()
        return Gson().fromJson(json, MinecraftPingReply::class.java)
    }
}