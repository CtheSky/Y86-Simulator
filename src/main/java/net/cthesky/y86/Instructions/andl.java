package net.cthesky.y86.Instructions;

import net.cthesky.y86.Simulator.Y86Simulator;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class andl extends opl{

    @Override
    public byte firstByte() {
        return (byte)0x62;
    }

    @Override
    public int op(int a, int b) {
        return a & b;
    }

    @Override
    public void setCC(int a, int b, Y86Simulator simulator) {
        int t = op(a, b);
        simulator.ZF = t == 0;
        simulator.SF = t < 0;
        simulator.OF = false;
    }
}
