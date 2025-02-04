package com.ktb.automessage.utils;


import com.google.gson.Gson;
import com.ktb.automessage.user.CLI_User;
import com.ktb.automessage.user.KTBUser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class UserDataUtil {
    private static final String DATA_PATH = "src/data.json";
    private static final ArrayList<KTBUser> userData = new ArrayList<>();

    public static ArrayList<KTBUser> loadUserData() {
        try (FileReader reader = new FileReader(DATA_PATH)) {
            Gson gson = new Gson();
            CLI_User[] users = gson.fromJson(reader, CLI_User[].class);
            for (CLI_User user : users) userData.add(user.toKTBUser());
            return userData;
        } catch (IOException e) {
            System.out.println("❌ KTB AutoMessage : KTB Member를 불러오는데 실패했습니다." + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveUserData() {

    }
}
