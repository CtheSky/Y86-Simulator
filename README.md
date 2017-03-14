# Y86-Simulator


This is a simulator for Y86 assembly language written in Java. 
See more about [Y86 Instruction Set Architecture](http://acm.hit.edu.cn/hoj/static/img/pic/100603-B.htm)

# Usage
You could download `Y86Simulator.jar` in [release](https://github.com/CtheSky/Y86-Simulator/releases).
```shell
>java -jar Y86Simulator.jar asum.yo
State: HLT
PC: 0x17
Condition Codes:  ZF: 1 SF: 0 OF: 0
Changed Register State:
%eax:0x00000000     0x0000abcd
%ecx:0x00000000     0x00000024
%edx:0x00000000     0x00000000
%ebx:0x00000000     0xffffffff
%esp:0x00000000     0x00000100
%ebp:0x00000000     0x00000100
%esi:0x00000000     0x0000a000
%edi:0x00000000     0x00000000
Changed Memory State:
0x0000:00000000     0x30f40001
0x0004:00000000     0x000030f5
0x0008:00000000     0x00010000
0x000c:00000000     0x80240000
0x0014:00000000     0x0d000000
0x0018:00000000     0xc0000000
0x001c:00000000     0x000b0000
0x0020:00000000     0x00a00000
0x0024:00000000     0xa05f2045
...
```

Let's see the entry-point of `Y86Simulator.jar` and you will find it easy to work with it:
```java
public class Main {
    public static void main(String[] args) {
        if(args.length == 0) {
            System.err.println("No arguments were given");
        } else {
            try {
                File file = new File(args[0]);

                // Get XX.yo file content
                String input = new Scanner(file).useDelimiter("\\Z").next();
                String[] lines = input.split("\n");

                // Create Memory object and load y86 code into it
                Memory memo = new Memory();
                Y86Loader.load(lines, memo);

                // Create Simulator with Memory set above and run code
                Y86Simulator simulator = new Y86Simulator(memo);
                simulator.run();

                // Print simulator status
                System.out.println(simulator.getResult());

            } catch (FileNotFoundException e) {
                System.err.println("Couldn't find file :" + args[0]);
            } catch (IllegalStateException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
```

# TODO
* A gdb-like debug mode
* A GUI mode
