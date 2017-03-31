package pl.edu.pwr.mateusznowak.lab1.swim_lab1.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.R;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.BmiCondition;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations.BmiCounterAbstractFactory;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations.BmiCounterForImperialUnits;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations.BmiCounterForSiUnits;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.interfaces.IBmiCounter;

public class BmiCounterActivity extends AppCompatActivity {

    @BindView(R.id.editText_Height) EditText heightEditText;
    @BindView(R.id.editText_Mass) EditText massEditText;
    @BindView(R.id.textView_Height) TextView heightTextView;
    @BindView(R.id.textView_Mass) TextView massTextView;
    @BindView(R.id.textView_YourBMI) TextView yourBmiTextView;
    @BindView(R.id.button_countBmi) Button countBmiButton;
    @BindView(R.id.textView_BmiCondition) TextView bmiConditionTextView;

    private IBmiCounter selectedBmiCounter;
    //Toole: Apium, Espresso

    //ma miec mass do wpisania, height, do wpsania, count bmi button i wyswietlac BMI, pan uzywa massET (ze to jest masa edit text
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initBmiCounter();
        setupUserInterfaceDefaults();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(massAndHeightFieldsNotEmpty())
            countBmi();
    }


    private void initBmiCounter(){
        selectedBmiCounter = new BmiCounterForSiUnits();
    }

    private void setupUserInterfaceDefaults(){
        setupProperUnitsSymbols();
        setAllTextViewsToDefaultValues();
    }

    private void setAllTextViewsToDefaultValues() {
        massEditText.getText().clear();
        heightEditText.getText().clear();
        yourBmiTextView.setText(getString(R.string.your_bmi,getString(R.string.empty_bmi)));
        bmiConditionTextView.setText(getString(R.string.empty_bmi));
    }

    private void setupProperUnitsSymbols() {
        if(isImperialBmiCounterSelected()) {
            massTextView.setText(getString(R.string.mass, getString(R.string.pound_lb)));
            heightTextView.setText(getString(R.string.height, getString(R.string.inch_in)));
        }else if (isSiBmiCounterSelected()){
            massTextView.setText(getString(R.string.mass, getString(R.string.kilogram_kg)));
            heightTextView.setText(getString(R.string.height, getString(R.string.meter_m)));
        }
    }

    private boolean isImperialBmiCounterSelected(){
        return selectedBmiCounter instanceof BmiCounterForImperialUnits;
    }

    private boolean isSiBmiCounterSelected(){
        return selectedBmiCounter instanceof BmiCounterForSiUnits;
    }

    @OnClick(R.id.button_countBmi)
    public void onCountBmiButtonClicked(View view){
        countBmi();
    }

    public void countBmi(){
        if(massAndHeightFieldsNotEmpty()) {
            try {
                countBmiOnNotEmptyFields();
            }catch(IllegalArgumentException e){
                showUnrealInputToast();
            }
        }else{
            showInvalidFieldsToast();
        }
    }

    private void countBmiOnNotEmptyFields() {
        final Float countedBmi = getCurrentCountedBmi();
        updateBmiValueTextView(countedBmi);
        updateBmiConditionTextView(countedBmi);
    }

    private Float getCurrentCountedBmi(){
        return selectedBmiCounter.countBMI(
                Float.valueOf(massEditText.getText().toString()),
                Float.valueOf(heightEditText.getText().toString())
        );
    }
    private void updateBmiValueTextView(Float countedBmi){
        yourBmiTextView.setText(countedBmi.toString());
    }

    private void updateBmiConditionTextView(float countedBmi){
        BmiCondition bmiCondition = BmiCondition.getProperBmiCondition(countedBmi);
        if(bmiCondition==BmiCondition.OVERWEIGHT) {
            bmiConditionTextView.setText(getString(R.string.overweight));
            bmiConditionTextView.setTextColor(getResources().getColor(R.color.colorOverweight));
        }else if (bmiCondition == BmiCondition.UNDERWEIGHT){
            bmiConditionTextView.setText(getString(R.string.underweight));
            bmiConditionTextView.setTextColor(getResources().getColor(R.color.colorUnderweight));
        }else{
            bmiConditionTextView.setText(getString(R.string.correct));
            bmiConditionTextView.setTextColor(getResources().getColor(R.color.colorCorrectWeight));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.units_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                onBmiCounterSelected(item);
                setupUserInterfaceDefaults();
                return true;
        }
    }

    private void onBmiCounterSelected(MenuItem item){
        item.setChecked(!item.isChecked());
        if(item.getItemId() == R.id.menu_imp_units) {
            selectedBmiCounter = BmiCounterAbstractFactory.getBmiCounter(
                    BmiCounterAbstractFactory.BmiCounterUnits.IMPERIALS
            );
        }else if(item.getItemId() == R.id.menu_si_units){
            selectedBmiCounter = BmiCounterAbstractFactory.getBmiCounter(
                    BmiCounterAbstractFactory.BmiCounterUnits.SI
            );
        }
    }

    private void showInvalidFieldsToast(){
        Toast.makeText(this,R.string.invalidate_field_message,Toast.LENGTH_SHORT).show();
    }

    private void showUnrealInputToast(){
        Toast.makeText(this,R.string.unreal_input_message,Toast.LENGTH_SHORT).show();
    }

    private boolean massAndHeightFieldsNotEmpty(){
        return !TextUtils.isEmpty(heightEditText.getText()) &&
                !TextUtils.isEmpty(massEditText.getText());
    }
}
