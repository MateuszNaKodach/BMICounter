package pl.edu.pwr.mateusznowak.lab1.swim_lab1.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.R;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.BmiCondition;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations.BmiCounterAbstractFactory;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations.BmiCounterForImperialUnits;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations.BmiCounterForSiUnits;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.implementations.BmiCounterUnits;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.buisnesslogic.interfaces.IBmiCounter;
import pl.edu.pwr.mateusznowak.lab1.swim_lab1.util.Intents;

public class BmiCounterActivity extends AppCompatActivity {

    private static final String SHARED_PREFERENCES_MASS = "pl.edu.pwr.mateusznowak.lab1.swim_lab1.SHARED_PREFERENCES_MASS";
    private static final String SHARED_PREFERENCES_HEIGHT = "pl.edu.pwr.mateusznowak.lab1.swim_lab1.SHARED_PREFERENCES_HEIGHT";
    private static final String SHARED_PREFERENCES_UNITS = "pl.edu.pwr.mateusznowak.lab1.swim_lab1.SHARED_PREFERENCES_UNITS";

    private static final String INSTANT_STATE_MASS = "pl.edu.pwr.mateusznowak.lab1.swim_lab1.INSTANT_STATE_MASS";
    private static final String INSTANT_STATE_HEIGHT = "pl.edu.pwr.mateusznowak.lab1.swim_lab1.INSTANT_STATE_HEIGHT";
    private static final String INSTANT_STATE_UNITS = "pl.edu.pwr.mateusznowak.lab1.swim_lab1.INSTANT_STATE_UNITS";


    @BindView(R.id.editText_Height)
    EditText heightEditText;
    @BindView(R.id.editText_Mass)
    EditText massEditText;
    @BindView(R.id.textView_Height)
    TextView heightTextView;
    @BindView(R.id.textView_Mass)
    TextView massTextView;
    @BindView(R.id.textView_BMI)
    TextView countedBmiTextView;
    @BindView(R.id.button_countBmi)
    Button countBmiButton;
    @BindView(R.id.textView_BmiCondition)
    TextView bmiConditionTextView;

    private Menu optionsMenu;

    private SharedPreferences sharedPreferences;

    private IBmiCounter selectedBmiCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initSharedPreferences();
        initBmiCounter();
        setupUserInterfaceDefaults();
        loadMassAndHeightSharedPreferences();
    }



    private void initSharedPreferences() {
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
    }

    private void setupUserInterfaceDefaults() {
        setupProperUnitsSymbols();
        setAllTextViewsToDefaultValues();
    }

    private void setAllTextViewsToDefaultValues() {
        massEditText.getText().clear();
        heightEditText.getText().clear();
        countedBmiTextView.setText(getString(R.string.empty_bmi));
        bmiConditionTextView.setText(getString(R.string.empty_bmi));
        bmiConditionTextView.setTextColor(Color.BLACK);
    }

    private void setupProperUnitsSymbols() {
        if (isImperialBmiCounterSelected()) {
            massTextView.setText(getString(R.string.mass, getString(R.string.pound_lb)));
            heightTextView.setText(getString(R.string.height, getString(R.string.inch_in)));
        } else if (isSiBmiCounterSelected()) {
            massTextView.setText(getString(R.string.mass, getString(R.string.kilogram_kg)));
            heightTextView.setText(getString(R.string.height, getString(R.string.meter_m)));
        }
    }

    private boolean isImperialBmiCounterSelected() {
        return selectedBmiCounter instanceof BmiCounterForImperialUnits;
    }

    private boolean isSiBmiCounterSelected() {
        return selectedBmiCounter instanceof BmiCounterForSiUnits;
    }

    private void loadMassAndHeightSharedPreferences() {
        massEditText.setText(sharedPreferences.getString(SHARED_PREFERENCES_MASS, ""));
        heightEditText.setText(sharedPreferences.getString(SHARED_PREFERENCES_HEIGHT, ""));
        selectedBmiCounter = BmiCounterAbstractFactory.getBmiCounter(
                sharedPreferences.getString(SHARED_PREFERENCES_UNITS, BmiCounterUnits.SI.getName()));
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (massAndHeightAreValid()) {
            countBmi();
        }
        setupProperUnitsSymbols();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        optionsMenu = menu;
        updateSaveMenuOptionState();
        updateShareMenuOptionState();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_imp_units:
                onBmiCounterSelected(item);
                setupUserInterfaceDefaults();
                return true;
            case R.id.menu_si_units:
                onBmiCounterSelected(item);
                setupUserInterfaceDefaults();
                return true;
            case R.id.menu_save:
                saveMassAndHeightIfValid();
                return true;
            case R.id.menu_share:
                invokeShareBmiIntent();
                return true;
            case R.id.menu_author:
                invokeAuthorActivity();
                return true;
            default:
                return false;
        }
    }

    private void onBmiCounterSelected(MenuItem item) {
        item.setChecked(!item.isChecked());
        if (item.getItemId() == R.id.menu_imp_units) {
            selectedBmiCounter = BmiCounterAbstractFactory.getBmiCounter(
                    BmiCounterUnits.IMPERIALS
            );
        } else if (item.getItemId() == R.id.menu_si_units) {
            selectedBmiCounter = BmiCounterAbstractFactory.getBmiCounter(
                    BmiCounterUnits.SI
            );
        }
    }

    private void invokeShareBmiIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getCountedBmiConditionShareText());
        sendIntent.setType("text/plain");

        if(Intents.isIntentSafe(BmiCounterActivity.this,sendIntent)) {
            startActivity(sendIntent);
        }
    }

    private String getCountedBmiConditionShareText() {
        if (bmiConditionTextView.getText().toString().equals(getString(R.string.correct))) {
            return getString(R.string.share_correct_bmi, countedBmiTextView.getText());
        } else {
            return getString(R.string.share_bad_bmi, countedBmiTextView.getText());
        }
    }

    private void invokeAuthorActivity() {
        Intent intent = new Intent(BmiCounterActivity.this, AuthorActivity.class);
        startActivity(intent);
    }

    @OnTextChanged(R.id.editText_Mass)
    public void onMassEditTextChanged() {
        updateSaveMenuOptionState();
    }

    @OnTextChanged(R.id.editText_Height)
    public void onHeightEditTextChanged() {
        updateSaveMenuOptionState();
    }

    @OnTextChanged(R.id.textView_BmiCondition)
    public void onBmiConditionTextViewChanged() {
        updateShareMenuOptionState();
    }

    private void updateSaveMenuOptionState() {
        if (optionsMenu != null) {
            MenuItem saveMenuItem = optionsMenu.findItem(R.id.menu_save);
            saveMenuItem.setEnabled(massAndHeightAreValid());
        }
    }

    private void updateShareMenuOptionState() {
        if (optionsMenu != null) {
            MenuItem shareMenuItem = optionsMenu.findItem(R.id.menu_share);
            shareMenuItem.setEnabled(isBmiAlreadyCounted());
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(INSTANT_STATE_MASS, massEditText.getText().toString());
        outState.putString(INSTANT_STATE_HEIGHT,heightEditText.getText().toString());
        outState.putString(INSTANT_STATE_UNITS,selectedBmiCounter.getBmiCounterUnitsName());
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        massEditText.setText(savedInstanceState.getString(INSTANT_STATE_MASS));
        heightEditText.setText(savedInstanceState.getString(INSTANT_STATE_HEIGHT));
        selectedBmiCounter = BmiCounterAbstractFactory.getBmiCounter(
                savedInstanceState.getString(INSTANT_STATE_UNITS));
    }

    private void initBmiCounter() {
        selectedBmiCounter = new BmiCounterForSiUnits();
    }


    @OnClick(R.id.button_countBmi)
    public void countBmi() {
        if (massAndHeightAreValid()) {
            try {
                countBmiOnNotEmptyFields();
            } catch (IllegalArgumentException e) {
                showUnrealInputToast();
            }
        } else {
            showInvalidFieldsToast();
        }
    }

    private void countBmiOnNotEmptyFields() {
        final Float countedBmi = getCurrentCountedBmi();
        updateBmiValueTextView(countedBmi);
        updateBmiConditionTextView(countedBmi);
    }

    private Float getCurrentCountedBmi() {
        return selectedBmiCounter.countBMI(
                Float.valueOf(massEditText.getText().toString()),
                Float.valueOf(heightEditText.getText().toString())
        );
    }

    private void updateBmiValueTextView(Float countedBmi) {
        DecimalFormat df = new DecimalFormat("##.00");
        countedBmiTextView.setText(df.format(countedBmi));
    }

    private void updateBmiConditionTextView(float countedBmi) {
        BmiCondition bmiCondition = BmiCondition.getProperBmiCondition(countedBmi);
        if (bmiCondition == BmiCondition.OVERWEIGHT) {
            bmiConditionTextView.setText(getString(R.string.overweight));
            bmiConditionTextView.setTextColor(getResources().getColor(R.color.colorOverweight));
        } else if (bmiCondition == BmiCondition.UNDERWEIGHT) {
            bmiConditionTextView.setText(getString(R.string.underweight));
            bmiConditionTextView.setTextColor(getResources().getColor(R.color.colorUnderweight));
        } else {
            bmiConditionTextView.setText(getString(R.string.correct));
            bmiConditionTextView.setTextColor(getResources().getColor(R.color.colorCorrectWeight));
        }
    }


    private void saveMassAndHeightIfValid() {
        if (massAndHeightAreValid())
            saveMassAndHeightToSharedPreferences();
        else
            showUnrealInputToast();
    }

    private void saveMassAndHeightToSharedPreferences() {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        sharedPreferencesEditor.putString(SHARED_PREFERENCES_MASS, massEditText.getText().toString());
        sharedPreferencesEditor.putString(SHARED_PREFERENCES_HEIGHT, heightEditText.getText().toString());
        sharedPreferencesEditor.putString(SHARED_PREFERENCES_UNITS, selectedBmiCounter.getBmiCounterUnitsName());
        sharedPreferencesEditor.apply();
    }


    private void showInvalidFieldsToast() {
        Toast.makeText(this, R.string.invalidate_field_message, Toast.LENGTH_SHORT).show();
    }

    private void showUnrealInputToast() {
        Toast.makeText(this, R.string.unreal_input_message, Toast.LENGTH_SHORT).show();
    }

    private boolean massAndHeightFieldsNotEmpty() {
        return !TextUtils.isEmpty(heightEditText.getText()) &&
                !TextUtils.isEmpty(massEditText.getText());
    }

    private boolean massAndHeightAreValid() {
        return massAndHeightFieldsNotEmpty() && selectedBmiCounter.isValidHeight(Float.valueOf(heightEditText.getText().toString())) &&
                selectedBmiCounter.isValidMass(Float.valueOf(massEditText.getText().toString()));
    }

    private boolean isBmiAlreadyCounted() {
        return !bmiConditionTextView.getText().toString().equals(getString(R.string.empty_bmi));
    }

}
