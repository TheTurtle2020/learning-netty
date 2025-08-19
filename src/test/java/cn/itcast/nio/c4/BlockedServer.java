package cn.itcast.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static cn.itcast.nio.c2.ByteBufferUtil.debugRead;

@Slf4j
public class BlockedServer {

    public static void main(String[] args) throws IOException {
        // 使用 nio 来理解阻塞模式, 单线程
        // 0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        // 1. 创建了服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();

        // 2. 绑定监听端口，便于客户端建立连接发送数据给服务器。
        ssc.bind(new InetSocketAddress(2002));

        // 3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            // 4. accept 建立与客户端连接， SocketChannel 用来与客户端之间通信
            log.debug("connecting...");
            //Channel是数据读写的通道
            /**
             * 该函数用于在给定的channel上接受一个连接请求，返回一个新的SocketChannel对象，该对象表示与客户端的连接。具体来说：
             * channel.accept()是一个阻塞操作，它会一直等待直到有客户端发起连接请求。
             * 当有客户端连接时，服务器端会返回一个新的SocketChannel对象sc，该对象与客户端的连接相关联。
             * 通过SocketChannel对象，可以进行读写操作，与客户端进行数据传输。
             * SocketChannel还可以用于配置连接的参数，如设置非阻塞模式、选择器等。
             * 总之，SocketChannel sc = channel.accept()是服务器端用于接受客户端连接请求的关键代码，通过它建立与客户端的连接，并进行后续的数据传输操作
             */
            SocketChannel sc = ssc.accept(); // accept()会阻塞方法，线程进入堵塞状态
            log.debug("connected... {}", sc);
            channels.add(sc);
            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
                log.debug("before read... {}", channel);
                channel.read(buffer); // 阻塞方法，线程进入堵塞状态，等待客户端发送数据
                buffer.flip();
                debugRead(buffer);
                buffer.clear();
                log.debug("after read...{}", channel);
            }
        }
    }

}
