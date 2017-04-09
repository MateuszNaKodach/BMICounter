package pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations;


/**
 * Created by Mateusz on 27.03.2017.
 */

public class BmiCounterForSiUnits extends BmiCounterBase {


    public BmiCounterForSiUnits(){
        initMinAndMaxValues();
    }

    @Override
    protected void initMinAndMaxValues() {
        bmiCounterUnits = BmiCounterUnits.SI;
        minHeight = 0.5f;
        maxHeight = 2.5f;
        minMass = 10;
        maxMass = 250;
    }
}
