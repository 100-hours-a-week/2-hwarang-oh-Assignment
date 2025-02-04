package com.ktb.automessage.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ktb.automessage.user.CLI_User;
import com.ktb.automessage.user.KTBUser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDataUtil Class는 User Data를 불러오고 저장하는 역할을 하는 Class
 */
public class UserDataUtil {
    private static final String DATA_PATH = "src/data.json";
    private static final ArrayList<KTBUser> userData = new ArrayList<>();

    public static ArrayList<KTBUser> loadUserData() {
        try (FileReader reader = new FileReader(DATA_PATH)) {
            Gson gson = new Gson();
            CLI_User[] users = gson.fromJson(reader, CLI_User[].class);
            for (CLI_User user : users)
                userData.add(user.toKTBUser());
            return userData;
        } catch (IOException e) {
            System.out.println("❌ KTB AutoMessage : KTB Member를 불러오는데 실패했습니다." + e.getMessage());
            return new ArrayList<>();
        }
    }

    public static void saveUserData(String newUser) {
        try {
            List<CLI_User> users = new ArrayList<>();
            try (FileReader reader = new FileReader(DATA_PATH)) {
                Gson gson = new Gson();
                CLI_User[] existingUsers = gson.fromJson(reader, CLI_User[].class);
                if (existingUsers != null) {
                    users = new ArrayList<>(List.of(existingUsers));
                }
            } catch (IOException e) {
                System.out.println("⚠ 기존 데이터 로드 실패, 새로운 파일을 생성합니다.");
            }

            int newId = users.isEmpty() ? 1001 : users.get(users.size() - 1).getId() + 1;

            CLI_User newUserObj = new CLI_User(newId, newUser);

            users.add(newUserObj);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (FileWriter writer = new FileWriter(DATA_PATH)) {
                gson.toJson(users, writer);
                System.out.println("✅ KTB AutoMessage : 새로운 유저가 추가되었습니다! [" + newUser + "]");
            }

        } catch (IOException e) {
            System.out.println("❌ KTB AutoMessage : 유저 데이터를 저장하는데 실패했습니다. " + e.getMessage());
        }
    }

}
