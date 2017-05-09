package com.example.social.classes;

import android.os.AsyncTask;

import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maxim on 03.05.2017.
 */

public class APIWorker {
    static String json;
    static String responseData;
    static int responseCode;

    public static boolean registationMethod(String userLogin, String userPassword,
                                            String passwordConfirm, String firstName,
                                            String lastName, String middleName)
            throws Exception {

        Map<String, String> hashMapParams = new HashMap<>();

        hashMapParams.put("login", userLogin);
        hashMapParams.put("password", userPassword);
        hashMapParams.put("passwordConfirm", passwordConfirm);
        hashMapParams.put("firstName", firstName);
        hashMapParams.put("lastName", lastName);
        hashMapParams.put("middleName", middleName);

        json = new GsonBuilder().create().toJson(hashMapParams, Map.class);

        RegistrationTask pt = new RegistrationTask();
        pt.execute();

        if (responseCode != 200) {
            return true;
        } else {
            return false;
        }
    }

    // HTTP Post request
    private static String makeRequest(String url, String postJsonData) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        responseData = "";

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(postJsonData);
        wr.flush();
        wr.close();

        int responceCode = con.getResponseCode();
        System.out.println("nSending 'POST' request to URL : " + url);
        System.out.println("Post Data : " + postJsonData);
        System.out.println("Response Code : " + responceCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        responseData = response.toString();
        responseCode = responceCode;

        System.out.println(responseData);
        return responseData;
    }

    private static class RegistrationTask extends AsyncTask<Void, Void, Void> {

        String responseString = "";

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String responseString = makeRequest(Data.URL + "register", json);
            } catch (Exception e) {
                System.out.println("Ну пиздец");
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void res) {
            super.onPostExecute(res);

        }
    }
}

