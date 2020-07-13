package com.example.firstapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.firstapp.MainActivity;
import com.example.firstapp.R;
import com.example.firstapp.User;
import com.example.firstapp.UserAPI;

import org.jetbrains.annotations.NotNull;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class login extends Fragment {
    private  EditText uname,password;
    private  Button log;
    private String pass,name;


    public login() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);

        uname =view.findViewById(R.id.uname_login);
        password = view.findViewById(R.id.pass_login);
        log = view.findViewById(R.id.login_btn);

         log.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 final User user = new User();
                 user.setUsername(uname.getText().toString());
                 user.setPassword(password.getText().toString());
                SendPostReq(user);
             }
         });



        return view;
    }

    private void SendPostReq(final User m) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build();
        Retrofit retrofit  = new Retrofit.Builder()
                .baseUrl(MainActivity.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<Void> call = userAPI.login(m);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
               if(response.code()==200){
                   Toast.makeText(getContext(), "You have logged in successully!!", Toast.LENGTH_SHORT).show();

               }else{
                   Toast.makeText(getContext(), "Failed to login!!!", Toast.LENGTH_SHORT).show();
               }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to get Response!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
