package Simulator;

import Instructions.*;

/**
 * Project: Y86-Simulator
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
    }

    /**
     * Simulate y86 code loaded
     * @throws IllegalStateException   when error occurs during execution
     */
    public void run() throws IllegalStateException {
        while (stat == 1) {
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

    private Instruction fetch() {
        int byte1 = memory.get(PC * 8, 1);
        int first = byte1 >> 4, second = byte1 & 0x0f;
        switch(first) {
            case 0: return new halt();
            case 1: return new nop();
            case 2:
                switch (second) {
                    case 0: return new rrmovl();
                    case 1: return new cmovle();
                    case 2: return new cmovl();
                    case 3: return new cmove();
                    case 4: return new cmovne();
                    case 5: return new cmovge();
                    case 6: return new cmovg();
                }
            case 3: return new irmovl();
            case 4: return new rmmovl();
            case 5: return new mrmovl();
            case 6:
                switch (second) {
                    case 0: return new addl();
                    case 1: return new subl();
                    case 2: return new andl();
                    case 3: return new xorl();
                }
            case 7:
                switch (second) {
                    case 0: return new jmp();
                    case 1: return new jle();
                    case 2: return new jl();
                    case 3: return new je();
                    case 4: return new jne();
                    case 5: return new jge();
                    case 6: return new jg();
                }
            case 8: return new call();
            case 9: return new ret();
            case 10: return new pushl();
            case 11: return new popl();
            default:
                stat = 4;
                String address = "address -> 0x" + Integer.toHexString(PC);
                String content = "content -> 0x" + Integer.toHexString(byte1);
                String msg = "Invalid instruction: " + address + content;
                throw new IllegalStateException(msg);
        }
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
