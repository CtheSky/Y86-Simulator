package Instructions;

import Simulator.Y86Simulator;

/**
 * Project: Y86-Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class jl extends jXX{
    @Override
    public boolean cond(Y86Simulator simulator) {
        return simulator.SF != simulator.OF;
    }
}
