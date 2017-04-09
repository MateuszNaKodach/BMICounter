package pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations;

import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.interfaces.IBmiCounter;

/**
 * Created by Mateusz on 30.03.2017.
 */

public abstract class BmiCounterBase implements IBmiCounter {

    protected BmiCounterUnits bmiCounterUnits;

    protected static float minHeight;
    protected static float maxHeight;
    protected static float minMass;
    protected static float maxMass;


    protected abstract void initMinAndMaxValues();

    @Override
    public boolean isValidMass(float mass) {
        return mass>=minMass&&mass<=maxMass;
    }

    @Override
    public boolean isValidHeight(float height) {
        return height>=minHeight&&height<=maxHeight;
    }

    @Override
    public float countBMI(float mass, float height) throws IllegalArgumentException {
        if(!isValidHeight(height) || !isValidMass(mass))
            throw new IllegalArgumentException("Mass or height value is invalid!");

        return mass/(height*height);
    }

    public BmiCounterUnits getBmiCounterUnits() {
        return bmiCounterUnits;
    }

    @Override
    public String getBmiCounterUnitsName() {
        return bmiCounterUnits.getName();
    }
}
