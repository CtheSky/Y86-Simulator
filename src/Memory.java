import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

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

    public int get(int addr) {
        int alignAddr = addr & 0xffffffe0;
        int offset = addr & 0x0000001f;

        int left32 = memo.getOrDefault(alignAddr, 0);
        int right32 = memo.getOrDefault(alignAddr + 32, 0);

        return left32 << offset | right32 >>> 1 >>> (31 - offset);
    }

    public Iterable<MemoLine> changedMemory() {
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
        Memory m = new Memory();

    }
}
