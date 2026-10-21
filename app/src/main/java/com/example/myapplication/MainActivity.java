package com.example.myapplication;

import static android.view.View.VISIBLE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity {
    private Button register;
    private EditText name;
    private EditText lastname;
    private EditText email;
    private TextView studentcard;
    private RadioGroup rgGrade;
    private Spinner Major_spinner;
    private CheckBox terms_conditions;
    private CheckBox notifications;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "StudentPrefs";
    private static final String KEY_STUDENT_DATA = "saved_student_data";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        initSpinner();
        setuplistener();
        // אתחול ה-SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

// בדיקה האם יש נתונים שמורים בפתיחת האפליקציה
        String savedData = sharedPreferences.getString(KEY_STUDENT_DATA, null);
        if (savedData != null)
        {
            studentcard.setText(savedData);
            studentcard.setVisibility(View.VISIBLE); // מציג את הכרטיס אם יש נתונים
        }
    }

    private void initViews() {
        register = findViewById(R.id.buttonRegister);
        name = findViewById(R.id.firstName);
        lastname=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        studentcard=findViewById(R.id.studentCard);
        rgGrade =findViewById(R.id.radioGroup);
        Major_spinner =findViewById(R.id.spinner);
        terms_conditions=findViewById(R.id.checkBox);
        notifications=findViewById(R.id.checkBox2);
    }
    private void setuplistener()
    {
        register.setOnClickListener(view -> {
            showAttributes();
        });
    }
    private void showAttributes() {
        String givenFirstName = name.getText().toString().trim();
        String givenLastName = lastname.getText().toString().trim();
        String givenEmail = email.getText().toString().trim();



        if (givenFirstName.isEmpty())
        {
            name.setError(getString((R.string.error_first_name)));
            name.requestFocus();
            return;
        }

        if (givenLastName.isEmpty())
        {
            lastname.setError(getString(R.string.error_last_name));
            lastname.requestFocus();
            return;
        }

        if(givenEmail.isEmpty())
        {
            email.setError(getString(R.string.error_email));
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(givenEmail).matches())
        {
            email.setError(getString(R.string.not_suitable_email));
            email.requestFocus();
            return;

        }

        String givenGrade=getselectedGrade();
        if (givenGrade==null) {
            Toast.makeText(this, getString(R.string.error_grade), Toast.LENGTH_SHORT).show();
            return;
        }

        String major = getSelectedMajor();

        if (major == null)
        {
            Toast.makeText(this, getString(R.string.error_major), Toast.LENGTH_SHORT).show();
            return;
        }

        // בדיקה האם המשתמש אישר את תנאי השימוש
        if (!terms_conditions.isChecked()) {
            Toast.makeText(this, getString(R.string.error_terms), Toast.LENGTH_SHORT).show();
            return;
        }

        String shownGivenFirstName = getString(R.string.first_name) + givenFirstName;
        String shownGivenLastName =getString(R.string.last_name) + givenLastName;
        String shownGivenEmail =getString(R.string.gmail) + givenEmail;
        String shownGivenGrade= getString(R.string.grade) + givenGrade;

        String shownMajor = "Major: " + major;
        boolean wantsNotifications = notifications.isChecked();
        String shownNotifications = "Notifications: " + (wantsNotifications ? "Yes" : "No");


        String all= shownGivenFirstName+ "\n" + shownGivenLastName+ "\n" + shownGivenEmail+ "\n" + shownGivenGrade+"\n"+shownMajor+"\n"+shownNotifications;
        studentcard.setText(all);

        turnVisible();
        student student = new student(
                givenFirstName,
                givenLastName,
                givenEmail,
                givenGrade,
                major,
                terms_conditions.isChecked(),
                notifications.isChecked()
        );
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_STUDENT_DATA, all);
        editor.apply();
    }

    private String getselectedGrade()
    {
        int selected_id=rgGrade.getCheckedRadioButtonId();
        if(selected_id==-1)
            return null;
        RadioButton select=findViewById(selected_id);
        return select.getText().toString();
    }
    private void turnVisible()
    {
        studentcard.setVisibility(VISIBLE);
    }
    private void initSpinner()
    {
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this,R.array.major_spinner, android.R.layout.simple_spinner_item);
       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
       Major_spinner.setAdapter(adapter);
    }

    private String getSelectedMajor()
    {
        int position =Major_spinner.getSelectedItemPosition();
        if (position <= 0)
            return null;
        return Major_spinner.getSelectedItem().toString();
    }




}