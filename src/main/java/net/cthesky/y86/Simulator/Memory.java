package net.cthesky.y86.Simulator;

import java.util.*;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/7
 * Description:
 * All rights reserved.
 */
public class Memory {
    private Map<Integer, Integer> memo = new HashMap<>();
    public class MemoLine{
        public int address;
        public int content;

        public MemoLine(int address, int content) {
            this.address = address;
            this.content = content;
        }
    }

    /**
     * Set the 32-bit content at given address
     * @param addr      int value representing address
     * @param content  int value representing 32-bit content
     */
    public void set(int addr, int content) {
        int alignAddr = addr & 0xffffffe0;
        int offset = addr & 0x0000001f;

        int left32 = memo.getOrDefault(alignAddr, 0);
        int right32 = memo.getOrDefault(alignAddr + 32, 0);

        left32 = left32 >> 1 >> (31 - offset) << 1 << (31 - offset) | content >>> offset;
        right32 = right32 << offset >>> offset | content << 1 << (31 - offset);

        memo.put(alignAddr, left32);
        memo.put(alignAddr + 32, right32);
    }

    /**
     * Set the [size * 8]-bit content at given address
     * @param addr      int value representing address
     * @param content  int value representing [size * 8] content, range from 0 to 4
     */
    public void set(int addr, int content, int size) {
        if (size <= 0 || size > 4)
            throw new IllegalArgumentException("net.cthesky.y86.Simulator.Memory.set(int addr, int content, int size):size should be 1~4.");

        int origin = get(addr);
        int mask = (1 << 8 * (4 - size)) - 1;
        content <<= 8 * (4 - size);
        content = origin & mask | content;

        set(addr, content);
    }

    /**
     * Get the 32-bit content at given address
     * @param addr     int value representing address
     * @return         int value representing 32-bit content
     */
    public int get(int addr) {
        int alignAddr = addr & 0xffffffe0;
        int offset = addr & 0x0000001f;

        int left32 = memo.getOrDefault(alignAddr, 0);
        int right32 = memo.getOrDefault(alignAddr + 32, 0);

        return left32 << offset | right32 >>> 1 >>> (31 - offset);
    }

    /**
     * Get the [size * 8]-bit content at given address
     * @param addr     int value representing address
     * @param size     int value representing how much byte of content to get
     * @return         int value representing [size * 8]-bit content, size
     */
    public int get(int addr, int size) {
        if (size <= 0 || size > 4)
            throw new IllegalArgumentException("net.cthesky.y86.Simulator.Memory.set(int addr, int content, int size):size should be 1~4.");

        int content = get(addr);
        return content >>> (4 - size) * 8;
    }

    /**
     * Get the [size * 8]-bit content at given address in byte reversed order
     *   suppose original content is 0x04030201, returns 0x01020304
     * @param addr     int value representing address
     * @param size     int value representing how much byte of content to get
     * @return         int value representing [size * 8]-bit content, size
     */
    public int getByteReversed(int addr, int size) {
        int content = get(addr, size);
        int reversed = 0;
        for (int i = 0; i < size; i++) {
            reversed <<= 8;
            reversed |= content & 0xff;
            content >>= 8;
        }
        return reversed;
    }

    /**
     * Set the [size * 8]-bit content at given address in byte reversed order
     *   suppose given content is 0x04030201, set memory to be 0x01020304
     * @param addr     int value representing address
     * @param content int value representing [size * 8]-bit content, size
     * @param size     int value representing how much byte of content to get
     */
    public void setByteReversed(int addr, int content, int size) {
        int reversed = 0;
        for (int i = 0; i < size; i++) {
            reversed <<= 8;
            reversed |= content & 0xff;
            content >>= 8;
        }
        set(addr, reversed, size);
    }

    /**
     * Returns changed memory info
     * @return     List of MemoLine, which has two int field: address, content
     */
    public List<MemoLine> changedMemory() {
        if (memo.isEmpty()) return null;

        ArrayList<MemoLine> lines = new ArrayList<>();
        for (int addr : memo.keySet()) {
            MemoLine line = new MemoLine(addr, memo.get(addr));
            if (line.content != 0)
                lines.add(line);
        }

        lines.sort(new Comparator<MemoLine>() {
            @Override
            public int compare(MemoLine o1, MemoLine o2) {
                return o1.address - o2.address;
            }
        });

        return lines;
    }
}
