package Instructions;

import Simulator.Y86Simulator;

/**
 * Project: Y86-Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class call implements Instruction {
    @Override
    public void execute(Y86Simulator simulator) {
        int pc = simulator.PC;

        Y86Simulator.Register esp = simulator.registers[4];

        int valC = simulator.memory.getByteReversed((pc + 1) * 8, 4);
        esp.value -= 4;
        simulator.memory.setByteReversed(esp.value * 8, pc + 5, 4);

        simulator.PC = valC;
    }
}
