package com.example.myapplication;

import static android.view.View.VISIBLE;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;

public class MainActivity extends AppCompatActivity {
    private Button register;
    private EditText name;
    private EditText lastname;
    private EditText email;
    private TextView studentcard;
    private RadioGroup rgGrade;

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
        setuplistener();
    }

    private void initViews() {
        register = findViewById(R.id.buttonRegister);
        name = findViewById(R.id.firstName);
        lastname=findViewById(R.id.lastName);
        email=findViewById(R.id.email);
        studentcard=findViewById(R.id.studentCard);
        rgGrade =findViewById(R.id.radioGroup);
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
            name.setError("You didnt type in youre first name");
            name.requestFocus();
            return;
        }

        if (givenLastName.isEmpty())
        {
            lastname.setError("You didnt type in youre last name");
            lastname.requestFocus();
            return;
        }

        if(givenEmail.isEmpty())
        {
            email.setError("You didnt type in youre email");
            email.requestFocus();
            return;
        }
        String givenGrade=getselectedGrade();
        if (givenGrade==null) {
            Toast.makeText(this, "Youd didnt choose a grade", Toast.LENGTH_SHORT).show();
            return;
        }

        String shownGivenFirstName = getString(R.string.first_name) + givenFirstName;
        String shownGivenLastName =getString(R.string.last_name) + givenLastName;
        String shownGivenEmail =getString(R.string.gmail) + givenEmail;
        String shownGivenGrade= getString(R.string.grade) + givenGrade;

        String all= shownGivenFirstName+ "\n" + shownGivenLastName+ "\n" + shownGivenEmail+ "\n" + shownGivenGrade;
        studentcard.setText(all);

        turnVisible();
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


}