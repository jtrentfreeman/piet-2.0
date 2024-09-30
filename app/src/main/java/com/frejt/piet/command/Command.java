package com.frejt.piet.command;

import java.util.UUID;

import com.frejt.piet.exception.PietCommandNotFoundException;
import com.frejt.piet.utils.Block;

/**
 * Commands are defined by the transition of colour from one colour block to hte
 * next as the interpreter travles through the program.
 * 
 * The number of steps along the Hue Cycle and Lightness Cycle in each
 * transition determine the command executed.
 */
public enum Command implements CommandInterface {
    
    NOP("nop", 0, 0) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.nop(uuid, older, newer);
        }
    },
    PUSH("push", 0, 1) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.push(uuid, older, newer);
        }
    },
    POP("pop", 0, 2) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.pop(uuid, older, newer);
        }
    },
    ADD("add", 1, 0) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.add(uuid, older, newer);
        }
    },
    SUB("subtract", 1, 1) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.subtract(uuid, older, newer);
        }
    },
    MULT("multiply", 1, 2) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.multiply(uuid, older, newer);
        }
    },
    DIV("divide", 2, 0) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.divide(uuid, older, newer);
        }
    },
    MOD("modulus", 2, 1) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.modulus(uuid, older, newer);
        }
    },
    NOT("not", 2, 2) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.not(uuid, older, newer);
        }
    },
    GREATER("greater", 3, 0) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.greater(uuid, older, newer);
        }
    },
    POINTER("dp_pointer", 3, 1) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.dp(uuid, older, newer);
        }
    },
    SWITCH("switch", 3, 2) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.cc(uuid, older, newer);
        }
    },
    DUP("duplicate", 4, 0) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.duplicate(uuid, older, newer);
        }
    },
    ROLL("roll", 4, 1) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.roll(uuid, older, newer);
        }
    },
    IN_NUM("in_num", 4, 2) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.inNum(uuid, older, newer);
        }
    },
    IN_CHAR("in_char", 5, 0) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.inChar(uuid, older, newer);
        }
    },
    OUT_NUM("out_num", 5, 1) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.outNum(uuid, older, newer);
        }
    },
    OUT_CHAR("out_char", 5, 2) {
        @Override
        public void calculate(UUID uuid, Block older, Block newer) {
            CommandController.outChar(uuid, older, newer);
        }
    };

    private String name;
    private Integer hue;
    private Integer light;

    private Command(String name, Integer hue, Integer light) {
        this.name = name;
        this.hue = hue;
        this.light = light;
    }

    public String getName() {
        return this.name;
    }

    public Integer getHue() {
        return this.hue;
    }

    public Integer getLight() {
        return this.light;
    }

    public static Command getCommand(Integer hue, Integer light) throws PietCommandNotFoundException {
        for (Command command : Command.values()) {
            if (hue == command.getHue()) {
                if (light == command.getLight()) {
                    return command;
                }
            }
        }

        throw new PietCommandNotFoundException("Difference between hue [" + hue + "] and light [" + light + "] is not supported");
    }

}
