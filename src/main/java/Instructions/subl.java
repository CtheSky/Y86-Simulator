package Instructions;

import Simulator.Y86Simulator;

/**
 * Project: Y86-Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public class subl extends opl {
    @Override
    public int op(int a, int b) {
        return b - a;
    }

    @Override
    public void setCC(int a, int b, Y86Simulator simulator) {
        int t = op(a, b);
        simulator.ZF = t == 0;
        simulator.SF = t < 0;
        simulator.OF = (a < 0 == t < 0) && (t < 0 != a < 0);
    }
}
