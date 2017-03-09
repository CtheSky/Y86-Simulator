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

    public void run() {
        while (stat == 1) {
            try {
                Instruction instruction = fetch();
                execute(instruction);
            } catch (IllegalStateException e) {
                break;
            }
        }
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
                throw new IllegalStateException();
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
