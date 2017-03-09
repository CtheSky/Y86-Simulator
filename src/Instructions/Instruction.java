package Instructions;
import Simulator.Y86Simulator;
/**
 * Project: Y86-Simulator
 * Author: CtheSky
 * Create Date: 2017/3/9
 * Description:
 * All rights reserved.
 */
public interface Instruction {
    void execute(Y86Simulator simulator);
}
