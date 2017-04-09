package pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations;

/**
 * Created by Mateusz on 20.03.2017.
 */

public class BmiCounterForImperialUnits extends BmiCounterBase {

    public BmiCounterForImperialUnits(){
        initMinAndMaxValues();
    }

    @Override
    protected void initMinAndMaxValues() {
        bmiCounterUnits = BmiCounterUnits.IMPERIALS;
        minHeight = 0.5f*39.4f;
        maxHeight = 2.5f*39.4f;
        minMass = 10*2.2f;
        maxMass = 250*2.2f;
    }

    @Override
    public float countBMI(float mass, float height) throws IllegalArgumentException {
        return super.countBMI(mass, height)*703;
    }
}
