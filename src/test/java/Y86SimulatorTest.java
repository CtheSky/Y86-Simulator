import net.cthesky.y86.Simulator.Memory;
import net.cthesky.y86.Simulator.Y86Loader;
import net.cthesky.y86.Simulator.Y86Simulator;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class Y86SimulatorTest {
    @Test
    public void asumTest() {
        try {
            URL url = Y86LoaderTest.class.getResource("resources/asum.yo");
            String input = new Scanner(new File(url.getPath())).useDelimiter("\\Z").next();

            String[] lines = input.split("\n");
            Memory memo = new Memory();
            Y86Loader.load(lines, memo);

            Y86Simulator simulator = new Y86Simulator(memo);
            simulator.run();

            // Status
            assertEquals(2, simulator.stat);

            // Condition Codes
            assertTrue(simulator.ZF);
            assertFalse(simulator.SF);
            assertFalse(simulator.OF);

            // Changed Register State
            assertEquals(0x0000abcd, simulator.registers[0].value);
            assertEquals(0x00000024, simulator.registers[1].value);
            assertEquals(0xffffffff, simulator.registers[3].value);
            assertEquals(0x00000100, simulator.registers[4].value);
            assertEquals(0x00000100, simulator.registers[5].value);
            assertEquals(0x0000a000, simulator.registers[6].value);

            // Changed Memory State
            assertEquals(0x000000f8, memo.getByteReversed(0x00e8 * 8, 4));
            assertEquals(0x0000003d, memo.getByteReversed(0x00ec * 8, 4));
            assertEquals(0x00000014, memo.getByteReversed(0x00f0 * 8, 4));
            assertEquals(0x00000004, memo.getByteReversed(0x00f4 * 8, 4));
            assertEquals(0x00000100, memo.getByteReversed(0x00f8 * 8, 4));
            assertEquals(0x00000011, memo.getByteReversed(0x00fc * 8, 4));
        } catch (FileNotFoundException e) {
            System.err.println("could not find file: asum.yo");
        }
    }

    @Test
    public void abs_asum_cmovTest() {
        try {
            URL url = Y86LoaderTest.class.getResource("resources/abs-asum-cmov.yo");
            String input = new Scanner(new File(url.getPath())).useDelimiter("\\Z").next();

            String[] lines = input.split("\n");
            Memory memo = new Memory();
            Y86Loader.load(lines, memo);

            Y86Simulator simulator = new Y86Simulator(memo);
            simulator.run();

            // Status
            assertEquals(2, simulator.stat);

            // Condition Codes
            assertTrue(simulator.ZF);
            assertFalse(simulator.SF);
            assertFalse(simulator.OF);

            // Changed Register State
//            assertEquals(0x0000abcd, simulator.registers[0].value);
            assertEquals(0x00000024, simulator.registers[1].value);
            assertEquals(0xffffffff, simulator.registers[3].value);
            assertEquals(0x000000f8, simulator.registers[4].value);
            assertEquals(0x00000100, simulator.registers[5].value);
            assertEquals(0x0000a000, simulator.registers[6].value);
            assertEquals(0x0000a000, simulator.registers[7].value);

            // Changed Memory State
            assertEquals(0x00000100, memo.getByteReversed(0x00f0 * 8, 4));
            assertEquals(0x00000039, memo.getByteReversed(0x00f4 * 8, 4));
            assertEquals(0x00000014, memo.getByteReversed(0x00f8 * 8, 4));
            assertEquals(0x00000004, memo.getByteReversed(0x00fc * 8, 4));
        } catch (FileNotFoundException e) {
            System.err.println("could not find file: abs-asum-cmov.yo");
        }
    }

    @Test
    public void abs_asum_jmpTest() {
        try {
            URL url = Y86LoaderTest.class.getResource("resources/abs-asum-jmp.yo");
            String input = new Scanner(new File(url.getPath())).useDelimiter("\\Z").next();

            String[] lines = input.split("\n");
            Memory memo = new Memory();
            Y86Loader.load(lines, memo);

            Y86Simulator simulator = new Y86Simulator(memo);
            simulator.run();

            // Status
            assertEquals(2, simulator.stat);

            // Condition Codes
            assertTrue(simulator.ZF);
            assertFalse(simulator.SF);
            assertFalse(simulator.OF);

            // Changed Register State
//            assertEquals(0x0000abcd, simulator.registers[0].value);
            assertEquals(0x00000024, simulator.registers[1].value);
            assertEquals(0xffffffff, simulator.registers[3].value);
            assertEquals(0x000000f8, simulator.registers[4].value);
            assertEquals(0x00000100, simulator.registers[5].value);
            assertEquals(0x0000a000, simulator.registers[6].value);
            assertEquals(0x0000a000, simulator.registers[7].value);

            // Changed Memory State
            assertEquals(0x00000100, memo.getByteReversed(0x00f0 * 8, 4));
            assertEquals(0x00000039, memo.getByteReversed(0x00f4 * 8, 4));
            assertEquals(0x00000014, memo.getByteReversed(0x00f8 * 8, 4));
            assertEquals(0x00000004, memo.getByteReversed(0x00fc * 8, 4));
        } catch (FileNotFoundException e) {
            System.err.println("could not find file: abs-asum-jmp.yo");
        }
    }
}
