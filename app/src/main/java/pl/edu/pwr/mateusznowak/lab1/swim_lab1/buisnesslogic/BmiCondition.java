package pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic;

/**
 * Created by Mateusz on 31.03.2017.
 */

public enum BmiCondition {
    UNDERWEIGHT,
    OVERWEIGHT,
    CORRECT;

    public static BmiCondition getProperBmiCondition(float bmi){
        if(bmi< 18.5f)
            return BmiCondition.UNDERWEIGHT;
        else if(bmi>=25.0f)
            return BmiCondition.OVERWEIGHT;
        else
            return BmiCondition.CORRECT;
    }
}
