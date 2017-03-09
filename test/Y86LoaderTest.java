import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

/**
 * Project: Y86-Simulator
 * Author: CtheSky
 * Create Date: 2017/3/8
 * Description:
 * All rights reserved.
 */
public class Y86LoaderTest {

    @Test
    public void asumTest() {
        try {
            URL url = Y86LoaderTest.class.getResource("y86code/asum.yo");
            String input = new Scanner(new File(url.getPath())).useDelimiter("\\Z").next();

            String[] lines = input.split("\n");
            Memory memo = new Memory();
            Y86Loader.load(lines, memo);

            assertEquals(memo.get(0x0), 0x30f40001);
            assertEquals(memo.get(0x20 * 8), 0x00a00000);
            assertEquals(memo.get(0x38 * 8), 0x80420000);
            assertEquals(memo.get(0x73 * 8), 0x745b0000);
        } catch (FileNotFoundException e) {
            System.out.println("could not find file: asum.yo");
        }
    }
}
