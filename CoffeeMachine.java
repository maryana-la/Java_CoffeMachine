package machine;

import java.util.Scanner;
import static java.lang.System.exit;

public class CoffeeMachine {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CoffeeMachineProcess myMachine = new CoffeeMachineProcess(400, 540, 120, 9, 550);
        System.out.println("Write action (buy, fill, take, remaining, exit):");
        while (true) {
            String action = sc.nextLine();
            myMachine.processLine(action);
        }
    }
}

enum MachineState {
    ACTION, BUY, REFILL
}

enum RefillStatus {
    WATER, MILK, BEANS, CUPS;
}

class CoffeeMachineProcess {
    MachineState current = MachineState.ACTION;
    RefillStatus refill = RefillStatus.WATER;
    int water;
    int milk;
    int beans;
    int cups;
    int money;

    // constructor
    public CoffeeMachineProcess(int water_, int milk_, int beans_, int cups_, int money_) {
        water = water_;
        milk = milk_;
        beans = beans_;
        cups = cups_;
        money = money_;
    }

    void processLine(String command) {
        switch (current) {
            case ACTION:
                switch (command) {
                    case "buy":
                        System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
                        current = MachineState.BUY;
                        break;
                    case "fill":
                        System.out.println("Write how many ml of water you want to add: ");
                        current = MachineState.REFILL;
                        refill = RefillStatus.WATER;
                        break;
                    case "take":
                        System.out.println("I gave you $" + money);
                        money = 0;
                        break;
                    case "remaining":
                        printStatus();
                        break;
                    case "exit":
                        exit(0);
                        break;
                }
                break;
            case BUY:
                switch (command) {
                    case "1":
                        tryToMakeCoffee(250, 0, 16, 4);
                        break;
                    case "2":
                        tryToMakeCoffee(350, 75, 20, 7);
                        break;
                    case "3":
                        tryToMakeCoffee(200, 100, 12, 6);
                        break;
                    case "back":
                        break;
                }
                current = MachineState.ACTION;
                break;
            case REFILL:
                switch (refill) {
                    case WATER:
                        water += Integer.parseInt(command);
                        refill = RefillStatus.MILK;
                        System.out.println("\nWrite how many ml of milk you want to add: ");
                        break;
                    case MILK:
                        milk += Integer.parseInt(command);
                        refill = RefillStatus.BEANS;
                        System.out.println("Write how many grams of coffee beans you want to add: ");
                        break;
                    case BEANS:
                        beans += Integer.parseInt(command);
                        refill = RefillStatus.CUPS;
                        System.out.println("Write how many disposable cups of coffee you want to add: ");
                        break;
                    case CUPS:
                        cups += Integer.parseInt(command);
                        refill = RefillStatus.WATER;
                        current = MachineState.ACTION;
                        break;
                }
                break;
        }
        if (current == MachineState.ACTION) {
            System.out.println("\nWrite action (buy, fill, take, remaining, exit):");
        }
    }

    private void tryToMakeCoffee(int water_, int milk_, int beans_, int money_){
        if (water_ > water || milk_ > milk || beans_ > beans || 1 > cups) {
            if (water_ > water)
                System.out.println("Sorry, not enough water!");
            if (milk_ > milk)
                System.out.println("Sorry, not enough milk!");
            if (beans_ > beans)
                System.out.println("Sorry, not enough beans!");
            if (cups < 1)
                System.out.println("Sorry, not enough cups!");
        }
        else {
            System.out.println("I have enough resources, making you a coffee!");
            water -= water_;
            milk -= milk_;
            beans -= beans_;
            cups -= 1;
            money += money_;
        }
    }

    void printStatus() {
        System.out.println();
        System.out.println("The coffee machine has:");
        System.out.println(water + " ml of water");
        System.out.println(milk + " ml of milk");
        System.out.println(beans + " g of coffee beans");
        System.out.println(cups + " disposable cups");
        System.out.println("$" + money + " of money");
    }
}
