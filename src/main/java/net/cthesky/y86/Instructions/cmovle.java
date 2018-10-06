package net.cthesky.y86.Instructions;

import net.cthesky.y86.Simulator.Y86Simulator;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class cmovle extends cmovXX {
    @Override
    public boolean cond(Y86Simulator simulator) {
        return simulator.ZF || simulator.SF != simulator.OF;
    }
}
