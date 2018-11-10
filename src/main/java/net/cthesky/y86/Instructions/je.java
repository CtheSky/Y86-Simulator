package net.cthesky.y86.Instructions;

import net.cthesky.y86.Simulator.Y86Simulator;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class je extends jXX {

    @Override
    public byte firstByte() {
        return (byte)0x73;
    }

    @Override
    public boolean cond(Y86Simulator simulator) {
        return simulator.ZF;
    }
}
