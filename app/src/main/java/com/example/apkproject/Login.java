package com.example.apkproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity implements View.OnClickListener {

    UserLocalStorage userLocalStorage;
    EditText loginEditText, passwordEditText;
    Button signInButton;

    @Override
    protected void onStart() {
        overridePendingTransition(0, 0);
        super.onStart();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginEditText = (EditText)findViewById(R.id.login_edittext);
        passwordEditText = (EditText)findViewById(R.id.password_edittext);
        signInButton = (Button)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);
        userLocalStorage = new UserLocalStorage(this);
        Authenticate(userLocalStorage.GetLoggedUser());
    }

    private void logUserIn(User returnedUser) {
        userLocalStorage.StoreUserData(returnedUser);
        userLocalStorage.SetUserLoggedIn(true);

        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                User user = new User(login, password);
                Authenticate(user);
                break;
        }
    }

    private void Authenticate(User user){

        //place for server request

        if (user.login.equals("") && user.password.equals(""))
        {
            LogUserIn(user);
        }
        else
        {
            Log.e("login", "wrong data");
        }
    }

    private void LogUserIn(User user)
    {
        userLocalStorage.StoreUserData(user);
        userLocalStorage.SetUserLoggedIn(true);
        startActivity(new Intent(this, MainActivity.class));
    }

}
