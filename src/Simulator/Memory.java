package Simulator;

import java.util.*;

/**
 * Project: Y86-Simulator
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

    public void set(int addr, int content, int size) {
        if (size <= 0 || size > 4)
            throw new IllegalArgumentException("Simulator.Memory.set(int addr, int content, int size):size should be 1~4.");

        int origin = get(addr);
        int mask = (1 << 8 * (4 - size)) - 1;
        content <<= 8 * (4 - size);
        content = origin & mask | content;

        set(addr, content);
    }

    public int get(int addr) {
        int alignAddr = addr & 0xffffffe0;
        int offset = addr & 0x0000001f;

        int left32 = memo.getOrDefault(alignAddr, 0);
        int right32 = memo.getOrDefault(alignAddr + 32, 0);

        return left32 << offset | right32 >>> 1 >>> (31 - offset);
    }

    public int get(int addr, int size) {
        if (size <= 0 || size > 4)
            throw new IllegalArgumentException("Simulator.Memory.set(int addr, int content, int size):size should be 1~4.");

        int content = get(addr);
        return content >> (4 - size) * 8;
    }

    public List<MemoLine> changedMemory() {
        if (memo.isEmpty()) return null;

        ArrayList<MemoLine> lines = new ArrayList<>();
        for (int addr : memo.keySet()) {
            MemoLine line = new MemoLine(addr, memo.get(addr));
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

    public static void main(String[] args) {
        System.out.println(Integer.decode("11"));
    }
}
