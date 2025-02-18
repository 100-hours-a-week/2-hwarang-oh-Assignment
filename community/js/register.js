/**
 * IMP : 회원 가입 처리
 * TYPE 1: 입력 필드 Validation
 * TYPE 2: DataBase와 비교하여 중복된 이메일이 있는지 확인
 * TYPE 3: 비밀번호 일치 확인
 */
export async function renderRegister() {
  const registerForm = document.querySelector(".register-form");
  const registerButton = document.getElementById("registerButton");
  const emailInput = document.getElementById("email");
  const passwordInput = document.getElementById("password");
  const confirmPasswordInput = document.getElementById("confirmPassword");
  const nicknameInput = document.getElementById("nickname");
  const profileInput = document.getElementById("registerProfile");
  const previewImage = document.getElementById("previewImage");

  const db = await fetch("/data/db.json").then((res) => res.json());
  const users = db.users;

  // * Profile Image Preview
  profileInput.addEventListener("change", function () {
    const file = profileInput.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        previewImage.src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  });

  // * Register Form Validation -> 단순하게 비어있는지만 확인
  registerForm.addEventListener("input", function () {
    if (
      emailInput.value &&
      passwordInput.value &&
      confirmPasswordInput.value &&
      nicknameInput.value
    ) {
      registerButton.disabled = false;
      registerButton.classList.add("active");
    } else {
      registerButton.disabled = true;
      registerButton.classList.remove("active");
    }
  });

  // * Register Button Event -> 비밀번호 확인, 중복 이메일 확인
  registerButton.addEventListener("click", async function () {
    const email = emailInput.value.trim();
    const password = passwordInput.value.trim();
    const confirmPassword = confirmPasswordInput.value.trim();
    const nickname = nicknameInput.value.trim();

    if (password !== confirmPassword) {
      alert("비밀번호가 일치하지 않습니다.");
      return;
    }

    if (users.find((user) => user.email === email)) {
      alert("이미 가입된 이메일입니다.");
      return;
    }
    alert("회원가입이 완료되었습니다!");
    window.location.href = "/";
  });
}
