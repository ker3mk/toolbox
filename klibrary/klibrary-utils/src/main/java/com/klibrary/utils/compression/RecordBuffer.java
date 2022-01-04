package com.klibrary.utils.compression;

import java.nio.ByteBuffer;


public class RecordBuffer {
    private ByteBuffer buffer;

    public RecordBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public int getUnsignedShort() {
        return buffer.getShort() & 0x0000ffff;
    }

    public short getSignedShort() {
        return buffer.getShort();
    }

    public int getUnsignedShortLE() {
        short s = 0;
        s |= buffer.get() & 0xFF;
        s |= ((buffer.get() << 8) & 0xFF00);

        return s & 0x0000ffff;
    }

    public short getSignedShortLE() {
        short s = 0;
        s |= buffer.get() & 0xFF;
        s |= ((buffer.get() << 8) & 0xFF00);

        return s;
    }

    public int getSignedInt() {
        return buffer.getInt();
    }

    public long getUnsignedInt() {
        return buffer.getInt() & 0x00000000ffffffffL;
    }

    public int getSignedIntLE() {
        int i  = 0;
        i |= buffer.get() & 0xFF;
        i |= ((buffer.get() << 8) & 0xFF00);
        i |= ((buffer.get() << 16) & 0xFF0000);
        i |= ((buffer.get() << 24) & 0xFF000000);

        return i;
    }

    public long getUnsignedIntLE() {
        int i  = 0;
        i |= buffer.get() & 0xFF;
        i |= ((buffer.get() << 8) & 0xFF00);
        i |= ((buffer.get() << 16) & 0xFF0000);
        i |= ((buffer.get() << 24) & 0xFF000000);

        return i & 0x00000000ffffffffL;
    }

    public long getSignedLongLE() {
        long i  = 0;
        i |= buffer.get() & 0xFFL;
        i |= ((buffer.get() << 8) & 0xFF00L);
        i |= ((buffer.get() << 16) & 0xFF0000L);
        i |= ((buffer.get() << 24) & 0xFF000000L);
        i |= ((buffer.get() << 32) & 0xFF00000000L);
        i |= ((buffer.get() << 40) & 0xFF0000000000L);
        i |= ((buffer.get() << 48) & 0xFF000000000000L);
        i |= ((buffer.get() << 56) & 0xFF00000000000000L);

        return i;
    }

    public RecordBuffer copy(int size) {
        byte[] sessionBytes = new byte[size];
        try {
            buffer.get(sessionBytes);
        }catch (Exception e){
          //  logger.error("Buffer is not expected size... Expected size is:{}, Found Buffer Remaining Size :{}",size,buffer.array().length-buffer.position());
            //logger.error(e.getMessage(),e);
        }
        return new RecordBuffer(ByteBuffer.wrap(sessionBytes));
    }

    public byte getByte() {
        return buffer.get();
    }

    public int getUnsignedByte() {
        return 0xFF & buffer.get();
    }

    public ByteBuffer get(byte[] dst, int offset, int length) {
        return buffer.get(dst, offset, length);
    }

    public ByteBuffer get(byte[] dst) {
        return buffer.get(dst);
    }

    public boolean hasRemaining() {
        return buffer.hasRemaining();
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public byte[] array() {
        return buffer.array();
    }
}
