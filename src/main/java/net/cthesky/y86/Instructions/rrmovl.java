package net.cthesky.y86.Instructions;

import net.cthesky.y86.Simulator.Y86Simulator;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class rrmovl implements Instruction {

    @Override
    public byte firstByte() {
        return (byte)0x20;
    }

    @Override
    public void execute(Y86Simulator simulator) {
        int pc = simulator.PC;

        int byte2 = simulator.memory.get((pc + 1) * 8, 1);
        Y86Simulator.Register rA = simulator.registers[byte2 >> 4];
        Y86Simulator.Register rB = simulator.registers[byte2 & 0x0f];

        rB.value = rA.value;

        simulator.PC = pc + 2;
    }
}
