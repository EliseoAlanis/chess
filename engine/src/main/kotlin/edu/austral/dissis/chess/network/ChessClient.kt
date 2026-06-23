package edu.austral.dissis.chess.network

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.ingsis.clientserver.Client
import edu.austral.ingsis.clientserver.ClientConnectionListener
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import java.net.InetSocketAddress

class ChessClient(
    private val serverAddress: String = "localhost",
    private val serverPort: Int = 8080,
) {
    private val client: Client

    // Callback para que la UI se entere de los cambios de estado enviados por el servidor
    var onGameStateReceived: ((GameStatePayload) -> Unit)? = null
    var onInitialConnection: ((InitialConnectionPayload) -> Unit)? = null

    init {
        client =
            NettyClientBuilder
                .createDefault()
                .withAddress(InetSocketAddress(serverAddress, serverPort))
                .withConnectionListener(
                    object : ClientConnectionListener {
                        override fun handleConnection() {
                            println("Cliente conectado exitosamente a \$serverAddress:\$serverPort")
                        }

                        override fun handleConnectionClosed() {
                            println("Cliente desconectado de \$serverAddress:\$serverPort")
                        }
                    },
                ).addMessageListener(
                    "initial_state",
                    object : TypeReference<Message<InitialConnectionPayload>>() {},
                    object : MessageListener<InitialConnectionPayload> {
                        override fun handleMessage(message: Message<InitialConnectionPayload>) {
                            onInitialConnection?.invoke(message.payload)
                        }
                    },
                ).addMessageListener(
                    "new_state",
                    object : TypeReference<Message<GameStatePayload>>() {},
                    object : MessageListener<GameStatePayload> {
                        override fun handleMessage(message: Message<GameStatePayload>) {
                            onGameStateReceived?.invoke(message.payload)
                        }
                    },
                ).build()
    }

    fun connect() {
        client.connect()
    }

    fun sendMove(move: MovePayload) {
        client.send(Message("move", move))
    }
}
