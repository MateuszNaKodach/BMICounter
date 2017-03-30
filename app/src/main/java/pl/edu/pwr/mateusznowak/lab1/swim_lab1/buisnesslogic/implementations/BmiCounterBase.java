package pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations;

import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.interfaces.IBmiCounter;

/**
 * Created by Mateusz on 30.03.2017.
 */

public abstract class BmiCounterBase implements IBmiCounter {

    protected static float minHeight = 0.5f;
    protected static float maxHeight = 2.5f;
    protected static float minMass = 10;
    protected static float maxMass = 250;


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

}
