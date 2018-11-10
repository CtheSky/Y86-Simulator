package net.cthesky.y86.Simulator;

import net.cthesky.y86.Instructions.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class Y86Simulator {
    public Register[] registers;
    public Memory memory;
    public boolean ZF, SF, OF;
    public int PC;
    public int stat;
    public Map<Integer, Integer> breakpoints;

    /**
     * Initialize simulator with given memory
     * @param memory    Memory object with y86 code loaded
     */
    public Y86Simulator(Memory memory) {
        registers = new Register[8];
        registers[0] = new Register("%eax");
        registers[1] = new Register("%ecx");
        registers[2] = new Register("%edx");
        registers[3] = new Register("%ebx");
        registers[4] = new Register("%esp");
        registers[5] = new Register("%ebp");
        registers[6] = new Register("%esi");
        registers[7] = new Register("%edi");

        this.memory = memory;
        PC = 0;
        stat = 1;
        breakpoints = new HashMap<>();
    }

    /**
     * Run y86 code loaded
     * @throws IllegalStateException   when error occurs during execution
     */
    public void run() throws IllegalStateException {
        while (stat == 1) {
            Instruction instruction = fetch();
            execute(instruction);
        }
    }

    /**
     * Run the code until a breakpoint is reached
     * @throws IllegalStateException   when error occurs during execution
     */
    public void continueToBreakpoint() throws IllegalStateException {
        while (stat == 1) {
            Instruction instruction = fetch();
            execute(instruction);
            if (breakpoints.containsKey(PC))
                break;
        }
    }

    /**
     * Run only one instruction
     * @throws IllegalStateException
     */
    public void step() throws IllegalStateException {
        if (stat == 1) {
            Instruction instruction = fetch();
            execute(instruction);
        }
    }

    /**
     * Returns a well-formatted representing all info of simulator
     * @return String
     */
    public String getResult() {
        String state = "State: " + getStateString();

        String pc = "PC: " + "0x" + PC;

        String cc = "Condition Codes: " +
                " ZF: " + (ZF ? "1": "0") +
                " SF: " + (SF ? "1": "0") +
                " OF: " + (OF ? "1": "0");

        String registerInfo = "Changed Register State:\n";
        for (Register r : registers) {
            String value = Integer.toHexString(r.value);
            if (value.length() < 8)
                value = "00000000".substring(0, 8 - value.length()) + value;
            registerInfo += r.name + ":0x00000000     " + "0x" + value + "\n";
        }

        String memoryInfo = "Changed Memory State:\n";
        for (Memory.MemoLine ml : memory.changedMemory()) {
            String addr = Integer.toHexString(ml.address / 8);
            if (addr.length() < 4)
                addr = "0000".substring(0, 4 - addr.length()) + addr;
            String content = Integer.toHexString(ml.content);
            if (content.length() < 8)
                content = "00000000".substring(0, 8 - content.length()) + content;
            memoryInfo += "0x" + addr + ":00000000     " + "0x" + content + "\n";
        }

        return state + "\n" + pc + "\n" + cc + "\n" + registerInfo + memoryInfo;
    }

    /**
     * Return state of simulator
     * @return String
     */
    public String getStateString() {
        if (stat == 1) return "AOK";
        if (stat == 2) return "HLT";
        if (stat == 3) return "ADR";
        if (stat == 4) return "INS";
        return "Unknown State";
    }

    /**
     * Returns the register object of given name, return null when name doesn't match any register
     * @param name    String of register name
     * @return        According register object
     */
    public Register getRegister(String name) {
        switch (name) {
            case "%eax": return registers[0];
            case "%ecx": return registers[1];
            case "%edx": return registers[2];
            case "%ebx": return registers[3];
            case "%esp": return registers[4];
            case "%ebp": return registers[5];
            case "%esi": return registers[6];
            case "%edi": return registers[7];
            default: return null;
        }
    }

    private Instruction fetch() {
        byte first = (byte)memory.get(PC * 8, 1);

        Instruction ins = InstructionDispatcher.getInstruction(first);
        if (ins == null) {
            stat = 4;
            String address = "address -> 0x" + Integer.toHexString(PC);
            String content = "content -> 0x" + Integer.toHexString(first);
            String msg = "Invalid instruction: " + address + content;
            throw new IllegalStateException(msg);
        }

        return ins;
    }

    private void execute(Instruction instruction) {
        instruction.execute(this);
    }

    public class Register {
        public String name;
        public int value;
        public Register(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name + "  ->  " + Integer.toHexString(value);
        }
    }
}
