package com.example.minecraft

import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

class MinecraftPingUtil {
    var PACKET_HANDSHAKE: Byte = 0x00
    var PACKET_STATUSREQUEST: Byte = 0x00
    var PACKET_PING: Byte = 0x01
    var PROTOCOL_VERSION = 4
    var STATUS_HANDSHAKE = 1

    fun validate(o: Any?, m: String?) {
        if (o == null) {
            throw RuntimeException(m)
        }
    }

    @Throws(IOException::class)
    fun io(b: Boolean, m: String?) {
        if (b) {
            throw IOException(m)
        }
    }

    /**
     * @author thinkofdeath
     * See: https://gist.github.com/thinkofdeath/e975ddee04e9c87faf22
     */
    @Throws(IOException::class)
    fun readVarInt(dis: DataInputStream): Int {
        var i = 0
        var j = 0
        while (true) {
            val k = dis.readByte().toInt()
            i = i or (k and 0x7F shl j++ * 7)
            if (j > 5) throw RuntimeException("VarInt too big")
            if (k and 0x80 != 128) break
        }
        return i
    }

    /**
     * @author thinkofdeath
     * See: https://gist.github.com/thinkofdeath/e975ddee04e9c87faf22
     * @throws IOException
     */
    @Throws(IOException::class)
    fun writeVarInt(out: DataOutputStream, paramInt: Int) {
        var mParamInt = paramInt
        while (true) {
            if (mParamInt and -0x80 == 0) {
                out.writeByte(mParamInt)
                return
            }
            out.writeByte(mParamInt and 0x7F or 0x80)
            mParamInt = mParamInt ushr 7
        }
    }
}