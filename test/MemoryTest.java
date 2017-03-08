import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Project: Y86-Simulator
 * Author: CtheSky
 * Create Date: 2017/3/7
 * Description:
 * All rights reserved.
 */
public class MemoryTest {
    @Test
    public void simpleUsageTest() {
        Memory memo = new Memory();
        assertNull(memo.changedMemory());

        memo.set(0x8, 0xffffffff);
        assertEquals(memo.get(8), 0xffffffff);
        assertEquals(memo.get(0), 0x00ffffff);
        assertEquals(memo.get(32), 0xff000000);

        List<Memory.MemoLine> changed = memo.changedMemory();
        assertEquals(changed.size(), 2);

        Memory.MemoLine line1 = changed.get(0);
        assertEquals(line1.address, 0);
        assertEquals(line1.content, 0x00ffffff);

        Memory.MemoLine line2 = changed.get(1);
        assertEquals(line2.address, 32);
        assertEquals(line2.content, 0xff000000);

    }
}
