const DB_STORAGE_KEY = 'KTBCOMMUNITY_DB';

export function setDB(data) {
  localStorage.setItem(DB_STORAGE_KEY, JSON.stringify(data));
}

export function getDB() {
  return JSON.parse(localStorage.getItem(DB_STORAGE_KEY)) || null;
}

export function removeDB() {
  localStorage.removeItem(DB_STORAGE_KEY);
}

export function registerUser(user) {
  const db = getDB();
  const users = db.users;
  if (users.find((eachUser) => eachUser.email === user.email)) {
    return { success: false, message: '이미 가입된 이메일입니다.' };
  }
  if (users.find((eachUser) => eachUser.nickname === user.nickname)) {
    return { success: false, message: '이미 사용 중인 닉네임입니다.' };
  }
  const newId = users.length > 0 ? Math.max(...users.map((u) => u.id)) + 1 : 1;
  const newUser = {
    id: newId,
    name: user.nickname,
    ...user,
  };

  users.push(newUser);
  setDB(db);

  return { success: true, message: '회원가입이 완료되었습니다!' };
}

export function updateUser(updatedUser) {
  const db = getDB();
  const users = db.users;
  const index = users.findIndex((user) => user.id === updatedUser.id);
  users[index] = updatedUser;
  setDB(db);
}

export function deleteUser(userId) {
  const db = getDB();
  db.users = db.users.filter((user) => user.id !== userId);
  setDB(db);
}
