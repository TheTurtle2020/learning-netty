package cn.itcast.netty.c4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

public class TestByteBuf {
        public static void main(String[] args) {

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        System.out.println(buf.getClass());
        System.out.println(buf.maxCapacity());
        log(buf);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append("a");
        }
        buf.writeBytes(sb.toString().getBytes());
        log(buf);
    }
//    public static void main(String[] args) {
//        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer(10);
//        buf.writeBytes(new byte[]{1, 2, 3, 4});
//        log(buf);
//        buf.markReaderIndex(); // mark
//        byte aByte = buf.readByte();
//        System.out.println("读取一个字节: " + aByte);
//        log(buf);
//
//        buf.resetReaderIndex(); // find mark
//        System.out.println("重置");
//        log(buf);
//        System.out.println("重新读取一个字节: " + buf.readByte());
//        log(buf);
//    }

    public static void log(ByteBuf buffer) {
        int length = buffer.readableBytes();
        int rows = length / 16 + (length % 15 == 0 ? 0 : 1) + 4;
        StringBuilder buf = new StringBuilder(rows * 80 * 2)
                .append("read index:").append(buffer.readerIndex())
                .append(" write index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf, buffer);
        System.out.println(buf.toString());
    }
}
