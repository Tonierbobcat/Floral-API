package com.loficostudios;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        new Test().start();
    }

    public static abstract class App {
        abstract void start();
    }

    public static class Test extends App {
        int startLevel = 1;
        int maxLevel = 256;
        int startExp = 0;
        int maxExp = 999999;


        @Override
        void start() {
            generateLevels();
        }

        public void generateLevels() {
            List<Integer> experience = new ArrayList<>();

            for (int i = 0; i < maxLevel; i++) {
                experience.add(getRequiredExperience(i + 1));
            }

            int index = 0;
            for (Integer i : experience) {
                System.out.println((index + 1) + ": " + i);
                index++;
            }
        }

        public int getRequiredExperience(int level) {
            double t = (double) (level - startLevel) / (maxLevel - startLevel);
            double exponent = 1.4635;
            t = Math.pow(t, exponent);
            return (int) (startExp + t * (maxExp - startExp));
        }
    }
}
