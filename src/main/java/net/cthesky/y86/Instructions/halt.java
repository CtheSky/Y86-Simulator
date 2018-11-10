package net.cthesky.y86.Instructions;

import net.cthesky.y86.Simulator.Y86Simulator;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class halt implements Instruction{

    @Override
    public byte firstByte() {
        return (byte)0x00;
    }

    @Override
    public void execute(Y86Simulator simulator) {
        simulator.stat = 2;
    }
}
