package Instructions;

import Simulator.Y86Simulator;

/**
 * Project: Y86-Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class halt implements Instruction{
    @Override
    public void execute(Y86Simulator simulator) {
        simulator.stat = 2;
    }
}
