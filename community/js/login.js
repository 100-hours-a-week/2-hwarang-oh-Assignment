/**
 * IMP : DataBase의 User Data와 입력된 User Data를 비교하여 로그인 처리
 * @param {*} email
 * @param {*} password
 */

export function renderLoginPage(USER_STORAGE_KEY) {
  const loginForm = document.querySelector(".login-form");
  loginForm.addEventListener("submit", function (event) {
    event.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    login(USER_STORAGE_KEY, email, password);
  });
}

async function login(USER_STORAGE_KEY, email, password) {
  try {
    const db = await fetch("/data/db.json").then((res) => res.json());
    const users = db.users;
    const user = users.find((user) => user.email === email && user.password === password);

    if (user) {
      sessionStorage.setItem(USER_STORAGE_KEY, JSON.stringify(user));
      alert(`KTB Community에 오신 것을 환영합니다, ${user.name}님!`);
      window.location.href = "/posts";
    } else alert("이메일 또는 비밀번호가 일치하지 않습니다.");
  } catch (error) {
    console.error("로그인 오류", error);
    alert("로그인 중 오류가 발생했습니다.");
  }
}
