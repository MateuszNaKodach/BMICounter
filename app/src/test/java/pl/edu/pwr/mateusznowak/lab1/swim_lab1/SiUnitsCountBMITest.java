package pl.edu.pwr.mateusznowak.lab1.swim_lab1;

import org.junit.Before;
import org.junit.Test;

import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations.BmiCounterForSiUnits;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.interfaces.IBmiCounter;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Mateusz on 20.03.2017.
 */

public class SiUnitsCountBMITest {

    private IBmiCounter countMBI;

    @Before
    public void initCountBMIForKgM(){
        countMBI = new BmiCounterForSiUnits();
    }

    @Test
    public void massUnder0Invalid() throws Exception{
        //GIVEN
        float testMass = -1.0f;
        //WHEN
        //IBmiCounter countMBI = new BmiCounterForSiUnits();
        //THEN
        boolean actual = countMBI.isValidMass(testMass);

        assertFalse(actual);
    }

    @Test
    public void mass0Invalid() throws Exception{
        //GIVEN
        float testMass = 0.0f;
        //WHEN
        //IBmiCounter countMBI = new BmiCounterForSiUnits();
        //THEN
        boolean actual = countMBI.isValidMass(testMass);

        assertFalse(actual);
    }

    @Test
    public void heightUnder0Invalid() throws Exception{
        float testHeight = -0.1f;

        boolean actual = countMBI.isValidHeight(testHeight);

        assertFalse(actual);
    }

    @Test
    public void heightAbove3IsInvalid() throws Exception{
        float testHeight = 3.5f;

        boolean actual = countMBI.isValidHeight(testHeight);

        assertFalse(actual);
    }


    @Test
    public void height2IsValid() throws Exception{
        float testHeight = 2.0f;

        boolean actual = countMBI.isValidHeight(testHeight);

        assertTrue(actual);
    }

    @Test
    public void height0IsInvalid() throws Exception{
        float testHeight = 0.0f;

        boolean actual = countMBI.isValidHeight(testHeight);

        assertFalse(actual);
    }



    @Test
    public void mass10IsValid() throws Exception{
        float testMass = 10;

        boolean actual = countMBI.isValidMass(testMass);

        assertTrue(actual);
    }


    @Test
    public void mass250IsValid() throws Exception{
        float testMass = 250;

        boolean actual = countMBI.isValidMass(testMass);

        assertTrue(actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void countBmiInvalidMassTest() throws Exception{
        float currentMass = 9;
        float currentHeight = 1.77f;

        countMBI.countBMI(currentMass,currentHeight);
    }

    @Test(expected = IllegalArgumentException.class)
    public void countBmiInvalidHeightTest() throws Exception{
        float currentMass = 20;
        float currentHeight = 3.0f;

        countMBI.countBMI(currentMass,currentHeight);
    }

    @Test
    public void countMyBmiTest() throws Exception{
        float myMass = 60;
        float myHeight = 1.77f;

        float myBMI = myMass/(myHeight*myHeight);

        float actual = countMBI.countBMI(myMass,myHeight);

        assertEquals(myBMI,actual,00.001);
    }


    @Test
    public void countMyBmiTest2() throws Exception{
        float myMass = 40;
        float myHeight = 1.77f;

        float myBMI = myMass/(myHeight*myHeight);

        float actual = countMBI.countBMI(myMass,myHeight);

        assertEquals(myBMI,actual,00.001);
    }
}
