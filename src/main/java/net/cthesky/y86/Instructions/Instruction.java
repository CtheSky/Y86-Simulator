package net.cthesky.y86.Instructions;
import net.cthesky.y86.Simulator.Y86Simulator;

/**
 * Project: Y86-net.cthesky.y86.Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public interface Instruction {

    // return first byte of the instruction
    // used by simulator to determine what is next instruction
    byte firstByte();

    void execute(Y86Simulator simulator);
}
