import { setCurrentUser, getCurrentUser } from "../main.js";

/**
 * IMP : 유저 정보 수정 페이지
 */
export function renderUserEditPage() {
  const userEditForm = document.querySelector(".user-edit-form");
  const editImageInput = document.getElementById("editImage");
  const previewImage = document.getElementById("previewImage");
  const emailInput = document.getElementById("email");
  const nicknameInput = document.getElementById("nickname");
  const userEditButton = document.getElementById("userEditButton");
  const toastMessage = document.getElementById("toastMessage");
  const user = getCurrentUser();
  console.log(user);

  // * Session Storage에 저장된 User 정보를 Form에 채워넣기
  emailInput.value = user.email;
  nicknameInput.value = user.name;
  previewImage.src = user.profileImage || "/image/placeholder.webp";

  // * Profile Image Preview
  editImageInput.addEventListener("change", function () {
    const file = editImageInput.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        previewImage.src = e.target.result;
      };
      reader.readAsDataURL(file);
    }
  });

  // * User 정보 수정 시, 입력값이 변경되었는지 확인
  userEditForm.addEventListener("input", function () {
    if (
      emailInput.value !== user.email ||
      nicknameInput.value !== user.nickname ||
      previewImage.src !== user.profileImage
    ) {
      userEditButton.disabled = false;
      userEditButton.classList.add("active");
    } else {
      userEditButton.disabled = true;
      userEditButton.classList.remove("active");
    }
  });

  // * User 정보 수정 버튼 클릭 시, Session Storage에 저장된 User 정보 업데이트
  userEditButton.addEventListener("click", function () {
    const updatedUser = {
      email: emailInput.value.trim(),
      id: user.id,
      name: nicknameInput.value.trim(),
      password: user.password,
      profileImage: previewImage.src,
    };
    setCurrentUser(updatedUser);
    toastMessage.classList.add("show");
    setTimeout(() => {
      toastMessage.classList.remove("show");
      window.location.href = "/posts"; // 홈으로 리디렉션
    }, 1000);
  });
}

/**
 * IMP : 비밀번호 변경 페이지
 */
export function renderPasswordChangePage() {
  const passwordInput = document.getElementById("password");
  const confirmPasswordInput = document.getElementById("confirmPassword");
  const passwordChangeButton = document.getElementById("passwordChangeButton");
  const passwordError = document.getElementById("passwordError");
  const confirmPasswordError = document.getElementById("confirmPasswordError");
  const toastMessage = document.getElementById("toastMessage");
  const user = getCurrentUser();

  //* 비밀번호 유효성 검사
  function validatePassword() {
    const password = passwordInput.value.trim();
    const confirmPassword = confirmPasswordInput.value.trim();
    let isValid = true;

    if (password.length < 6) {
      passwordError.textContent = "비밀번호는 최소 6자 이상이어야 합니다.";
      isValid = false;
    } else {
      passwordError.textContent = "";
    }

    if (confirmPassword && password !== confirmPassword) {
      confirmPasswordError.textContent = "비밀번호가 일치하지 않습니다.";
      isValid = false;
    } else {
      confirmPasswordError.textContent = "";
    }

    passwordChangeButton.disabled = !isValid;
    if (isValid) {
      passwordChangeButton.classList.add("active");
    } else {
      passwordChangeButton.classList.remove("active");
    }
  }

  // * 비밀번호 변경 될 때마다, 유효성 검사
  passwordInput.addEventListener("input", validatePassword);
  confirmPasswordInput.addEventListener("input", validatePassword);

  // * 비밀번호 변경 버튼 클릭 시, Session Storage에 저장된 User 정보 업데이트
  passwordChangeButton.addEventListener("click", function () {
    const newPassword = passwordInput.value.trim();

    user.password = newPassword;
    setCurrentUser(user);

    toastMessage.classList.add("show");
    setTimeout(() => {
      toastMessage.classList.remove("show");
      window.location.href = "/posts";
    }, 1000);
  });
}
