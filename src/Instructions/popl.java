package Instructions;

import Simulator.Y86Simulator;

/**
 * Project: Y86-Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class popl implements Instruction {
    @Override
    public void execute(Y86Simulator simulator) {
        int pc = simulator.PC;

        int byte2 = simulator.memory.get((pc + 1) * 8, 1);
        Y86Simulator.Register rA = simulator.registers[byte2 >> 4];
        Y86Simulator.Register esp = simulator.registers[4];

        rA.value = simulator.memory.getByteReversed(esp.value * 8, 4);
        esp.value += 4;

        simulator.PC = pc + 2;
    }
}
