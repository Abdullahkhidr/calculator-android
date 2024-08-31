package com.abdullah.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abdullah.calculator.Math.Expression;

public class MainActivity extends AppCompatActivity {
    TextView equation;
    private final View.OnClickListener onClickBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            addCharToEquation(((Button) v).getText().toString());
        }
    };
    Button clearBtn, dotBtn, equalBtn, plusBtn, minusBtn, multiplyBtn, divideBtn, backspaceBtn, remainderBtn, num0Btn, num1Btn, num2Btn, num3Btn, num4Btn, num5Btn, num6Btn, num7Btn, num8Btn, num9Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        declareElements();

        dotBtn.setOnClickListener(onClickBtn);
        plusBtn.setOnClickListener(onClickBtn);
        minusBtn.setOnClickListener(onClickBtn);
        num0Btn.setOnClickListener(onClickBtn);
        num1Btn.setOnClickListener(onClickBtn);
        num2Btn.setOnClickListener(onClickBtn);
        num3Btn.setOnClickListener(onClickBtn);
        num4Btn.setOnClickListener(onClickBtn);
        num5Btn.setOnClickListener(onClickBtn);
        num6Btn.setOnClickListener(onClickBtn);
        num7Btn.setOnClickListener(onClickBtn);
        num8Btn.setOnClickListener(onClickBtn);
        num9Btn.setOnClickListener(onClickBtn);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                equation.setText("");
            }
        });
        backspaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = equation.getText().toString();
                if (!str.isEmpty()) {
                    equation.setText(str.substring(0, str.length() - 1));
                }
            }
        });
        remainderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = equation.getText().toString();
                boolean valid = !str.isEmpty() && str.charAt(str.length() - 1) != '%';
                if (valid) {
                    addCharToEquation("%");
                }
            }
        });
        multiplyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = equation.getText().toString();
                boolean valid = !str.isEmpty() && str.charAt(str.length() - 1) != '*';
                if (valid) {
                    addCharToEquation("*");
                }
            }
        });
        divideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = equation.getText().toString();
                boolean valid = !str.isEmpty() && str.charAt(str.length() - 1) != '/';
                if (valid) {
                    addCharToEquation("/");
                }
            }
        });
        equalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Expression exp = new Expression(equation.getText().toString());
                    exp.evaluate();
                    equation.setText(exp.getResult());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addCharToEquation(String c) {
        equation.setText(equation.getText().toString() + c);
    }

    private void declareElements() {
        equation = findViewById(R.id.equationField);
        clearBtn = findViewById(R.id.clearBtn);
        dotBtn = findViewById(R.id.dotBtn);
        equalBtn = findViewById(R.id.equalBtn);
        plusBtn = findViewById(R.id.plusBtn);
        minusBtn = findViewById(R.id.minusBtn);
        multiplyBtn = findViewById(R.id.multiplyBtn);
        divideBtn = findViewById(R.id.divideBtn);
        backspaceBtn = findViewById(R.id.backspaceBtn);
        remainderBtn = findViewById(R.id.remainderBtn);
        num0Btn = findViewById(R.id.num0Btn);
        num1Btn = findViewById(R.id.num1Btn);
        num2Btn = findViewById(R.id.num2Btn);
        num3Btn = findViewById(R.id.num3Btn);
        num4Btn = findViewById(R.id.num4Btn);
        num5Btn = findViewById(R.id.num5Btn);
        num6Btn = findViewById(R.id.num6Btn);
        num7Btn = findViewById(R.id.num7Btn);
        num8Btn = findViewById(R.id.num8Btn);
        num9Btn = findViewById(R.id.num9Btn);
    }
}