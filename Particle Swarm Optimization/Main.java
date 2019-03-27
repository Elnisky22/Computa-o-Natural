package pso;

import java.util.Scanner;

public class Main {

    public static void main (String[] args) {
            System.out.println("Os valores a serem usados, são: ");
            System.out.println("X:  " + Swarm.DEFAULT_INERTIA);
            System.out.println("Y:  " + Swarm.DEFAULT_COGNITIVE);
            System.out.println("Social Component:    " + Swarm.DEFAULT_SOCIAL);
            menu(false);
            //menu(true);
    }

    private static void menu (boolean flag) {
        Swarm swarm;
        int particulas, rodadas;
        double x, y, social;
        particulas = getUserInt("Partículas: ");
        rodadas = getUserInt("Gerações:    ");

        if (flag) {
            x = getUserDouble("X: ");
            y = getUserDouble("Y: ");
            social = getUserDouble("Social:    ");
            swarm = new Swarm(particulas, rodadas, x, y, social);
        } else {
            swarm = new Swarm(particulas, rodadas);

        }

        swarm.run();
    }

    private static int getUserInt (String msg) {
        int input;
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);

            if (sc.hasNextInt()) {
                input = sc.nextInt();

                if (input <= 0) {
                    System.out.println("A quantia deve ser positiva.");
                } else {
                    break;
                }

            } else {
                System.out.println("Entrada inválida.");
            }
        }
        return input;
    }

    private static double getUserDouble (String msg) {
        double input;
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.print(msg);

            if (sc.hasNextDouble()) {
                input = sc.nextDouble();
                break;
            } else {
                System.out.println("Entrada inválida.");
            }
        }
        return input;
    }
}