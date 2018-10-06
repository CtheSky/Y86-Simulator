package net.cthesky.y86.Instructions;

import net.cthesky.y86.Simulator.Y86Simulator;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class ret implements Instruction {
    @Override
    public void execute(Y86Simulator simulator) {
        Y86Simulator.Register esp = simulator.registers[4];

        int valM = simulator.memory.getByteReversed(esp.value * 8, 4);
        esp.value += 4;

        simulator.PC = valM;
    }
}
