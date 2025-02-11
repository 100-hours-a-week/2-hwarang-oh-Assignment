package com.ktb.automessage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.ktb.automessage.domain.user.KTBUser;
import com.ktb.automessage.utils.ConsoleIOUtil;
import com.ktb.automessage.utils.UserDataUtil;

public class UserDataService {
    private final HashMap<Integer, KTBUser> userData;
    private final HashSet<String> trackingData;
    private final ConsoleIOUtil consoleIOUtil;

    public UserDataService(ConsoleIOUtil consoleIOUtil) {
        this.userData = new HashMap<>();
        this.trackingData = new HashSet<>();
        this.consoleIOUtil = consoleIOUtil;
    }

    public void loadUserData() {
        ArrayList<KTBUser> users = UserDataUtil.loadUserData();
        for (int i = 0; i < users.size(); i++) {
            this.userData.put(i + 1, users.get(i));
            this.trackingData.add(users.get(i).getFullName());
        }
    }

    public void saveUserData(KTBUser targetUser) {
        this.trackingData.add(targetUser.getFullName());
        this.userData.put(this.userData.size() + 1, targetUser);
        UserDataUtil.saveUserData(targetUser.getFullName());
    }

    public void displayUserData() {
        consoleIOUtil.defaultPrint("📋 사용자 목록을 불러오는 중입니다.");

        int size = this.userData.size();
        int columnCount = 4;
        int rowCount = (size + columnCount - 1) / columnCount;

        for (int i = 0; i < rowCount; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < columnCount; j++) {
                int index = i + j * rowCount;
                if (index < size) {
                    row.append(String.format("%-25s", (index + 1) + ". " + this.userData.get(index + 1)));
                }
            }
            System.out.println(row);
        }
        consoleIOUtil.defaultPrint("✅ 사용자 목록을 불러왔습니다.");
    }

    public boolean checkUserInData(int index) {
        return this.userData.containsKey(index);
    }

    public boolean checkUserInTrackingData(String fullName) {
        return this.trackingData.contains(fullName);
    }

    public KTBUser getUserData(int index) {
        return this.userData.get(index);
    }

}
