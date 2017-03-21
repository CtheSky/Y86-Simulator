# Y86-Simulator


This is a simulator for Y86 assembly language written in Java. 
See more about [Y86 Instruction Set Architecture](http://acm.hit.edu.cn/hoj/static/img/pic/100603-B.htm)

# Usage
### Simulate code
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
...
```
### Start gdb debug mode
```shell
>java -jar Y86Simulator.jar -debug asum.yo
Reading y86 code ... done.
(gdb)help
Supported commands:
break/b <lineNum>  -- add breakpoint at given line
run/r              -- run the program
continue/c         -- run into next breakpoint
step/s             -- run next instruction
display all        -- print all simulator status
display <address>  -- print 32-bit content at given address
display <register> -- print 32-bit content of given register
list all           -- print all loaded y86 code
list <n>           -- print 10 lines of y86 code around line n
quit/q             -- exit interactive debug mode

(gdb)
...
```
### Use as a lib
The entry-point of `Y86Simulator.jar` is `Simulator.Main` class which gives a simple example:
```java
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
```

# TODO
* A GUI mode
