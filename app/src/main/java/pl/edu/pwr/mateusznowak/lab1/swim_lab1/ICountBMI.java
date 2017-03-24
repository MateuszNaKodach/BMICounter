package pl.edu.pwr.mateusznowak.lab1.swim_lab1;

/**
 * Created by Mateusz on 20.03.2017.
 */

public interface ICountBMI {
    boolean isValidMass(float mass);
    boolean isValidHeight(float height);
    float countBMI(float mass, float height) throws IllegalArgumentException;
}
