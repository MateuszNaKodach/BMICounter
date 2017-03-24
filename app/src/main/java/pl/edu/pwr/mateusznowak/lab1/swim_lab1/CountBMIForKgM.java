package pl.edu.pwr.mateusznowak.lab1.swim_lab1;

/**
 * Created by Mateusz on 20.03.2017.
 */

public class CountBMIForKgM implements ICountBMI {

    public static final float MIN_HEIGHT = 0.5f;
    public static final float MAX_HEIGHT = 2.5f;
    public static final float MIN_MASS = 10;
    public static final float MAX_MASS = 250;

    @Override
    public boolean isValidMass(float mass) {
        return mass>=MIN_MASS&&mass<=MAX_MASS;
    }

    @Override
    public boolean isValidHeight(float height) {
        return height>=MIN_HEIGHT&&height<=MAX_HEIGHT;
    }

    @Override
    public float countBMI(float mass, float height) throws IllegalArgumentException {
        if(!isValidHeight(height) || !isValidMass(mass))
            throw new IllegalArgumentException("Niepoprawnie wprowadzone dane!");

        return mass/(height*height);
    }

}
