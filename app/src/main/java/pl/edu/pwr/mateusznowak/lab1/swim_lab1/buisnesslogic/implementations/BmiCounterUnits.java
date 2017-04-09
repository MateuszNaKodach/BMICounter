package pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations;

public enum BmiCounterUnits {
        SI("SI"),
        IMPERIALS("IMPERIALS");

        private String name;

        BmiCounterUnits(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
}
