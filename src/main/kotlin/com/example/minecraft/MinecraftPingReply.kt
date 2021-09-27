package com.example.minecraft

class MinecraftPingReply {
    private var description: Description? = null
    private var players: Players? = null
    private var version: Version? = null
    private var favicon: String? = null

    /**
     * @return the MOTD
     */
    fun getDescription(): Description? {
        return description
    }

    /**
     * @return @{link Players}
     */
    fun getPlayers(): Players? {
        return players
    }

    /**
     * @return @{link Version}
     */
    fun getVersion(): Version? {
        return version
    }

    /**
     * @return Base64 encoded favicon image
     */
    fun getFavicon(): String? {
        return favicon
    }

    class Description {
        /**
         * @return Server description text
         */
        var text: String? = null
    }

    class Players {
        /**
         * @return Maximum player count
         */
        var max = 0

        /**
         * @return Online player count
         */
        var online = 0

        /**
         * @return List of some players (if any) specified by server
         */
        var sample: List<Player>? = null
    }

    class Player {
        /**
         * @return Name of player
         */
        var name: String? = null

        /**
         * @return Unknown
         */
        var id: String? = null
    }

    class Version {
        /**
         * @return Version name (ex: 13w41a)
         */
        var name: String? = null

        /**
         * @return Protocol version
         */
        var protocol = 0
    }
}