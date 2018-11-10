package net.cthesky.y86.Instructions;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class InstructionDispatcher {
    private static final Map<Byte, Instruction> byte2instructon;


    static {
        byte2instructon = new HashMap<>();
        List<Instruction> instructions = Arrays.asList(
                new halt(),
                new nop(),
                new rrmovl(),
                new cmovle(),
                new cmovl(),
                new cmove(),
                new cmovne(),
                new cmovge(),
                new cmovg(),
                new irmovl(),
                new rmmovl(),
                new mrmovl(),
                new addl(),
                new subl(),
                new andl(),
                new xorl(),
                new jmp(),
                new jle(),
                new jl(),
                new je(),
                new jne(),
                new jge(),
                new jg(),
                new call(),
                new ret(),
                new pushl(),
                new popl()
        );

        for (Instruction i : instructions)
            byte2instructon.put(i.firstByte(), i);
    }

    public static Instruction getInstruction(byte b) {
        return byte2instructon.get(b);
    }
}
