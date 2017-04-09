package pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations;

import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.interfaces.IBmiCounter;

/**
 * Created by Mateusz on 30.03.2017.
 */

public abstract class BmiCounterAbstractFactory {

    public static IBmiCounter getBmiCounter(BmiCounterUnits bmiCounterUnits){
        if(bmiCounterUnits == BmiCounterUnits.IMPERIALS)
            return getBmiCounterForImperialUnits();
        else
            return getBmiCounterForSiUnits();
    }


    public static IBmiCounter getBmiCounter(String bmiCounterUnits){
        if(bmiCounterUnits == BmiCounterUnits.IMPERIALS.getName())
            return getBmiCounterForImperialUnits();
        else
            return getBmiCounterForSiUnits();
    }

    private static IBmiCounter getBmiCounterForImperialUnits(){
        return new BmiCounterForImperialUnits();
    }

    private static IBmiCounter getBmiCounterForSiUnits(){
        return new BmiCounterForSiUnits();
    }
}
