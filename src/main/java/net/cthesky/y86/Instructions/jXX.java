package net.cthesky.y86.Instructions;

import net.cthesky.y86.Simulator.Y86Simulator;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public abstract class jXX implements Instruction {

    @Override
    abstract public byte firstByte();

    @Override
    public void execute(Y86Simulator simulator) {
        int pc = simulator.PC;

        int valC = simulator.memory.getByteReversed((pc + 1) * 8, 4);
        int valP = pc + 5;

        boolean cond = cond(simulator);

        simulator.PC = cond ? valC : valP;
    }

    public abstract boolean cond(Y86Simulator simulator);
}
