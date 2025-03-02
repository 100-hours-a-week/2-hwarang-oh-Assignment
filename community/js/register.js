import {
  validateEmail,
  validatePassword,
  validateConfirmPassword,
  validateNickname,
} from './util_validator.js';

import { getDB, setDB, registerUser } from './util_database.js';

/**
 * IMP : 회원 가입 처리
 */
export async function renderRegister() {
  if (!getDB()) {
    const db = await fetch('/data/db.json').then((res) => res.json());
    setDB(db);
  }

  const newUser = {
    nickname: '',
    email: '',
    password: '',
    profileImage: '',
  };

  const validationState = {
    email: false,
    password: false,
    confirmPassword: false,
    nickname: false,
    profile: false,
  };

  const registerButton = document.getElementById('registerButton');
  function updateRegisterButtonState() {
    const allValid = Object.values(validationState).every(Boolean);
    registerButton.disabled = !allValid;
    registerButton.classList.toggle('active', allValid);
  }

  setupProfileInput(validationState, updateRegisterButtonState);
  setupEmailInput(validationState, updateRegisterButtonState);
  setupPasswordInput(validationState, updateRegisterButtonState);
  setupConfirmPasswordInput(validationState, updateRegisterButtonState);
  setupNicknameInput(validationState, updateRegisterButtonState);

  // TYPE : 회원가입 버튼 클릭 시
  registerButton.addEventListener('click', async function () {
    newUser.email = document.getElementById('email').value.trim();
    newUser.password = document.getElementById('password').value.trim();
    newUser.nickname = document.getElementById('nickname').value.trim();
    newUser.profileImage = document.getElementById('previewImage').src;

    let registerResponse = registerUser(newUser);
    if (!registerResponse.success) {
      alert(registerResponse.message);
      return;
    }
    alert('회원가입이 완료되었습니다!');
    window.location.href = '/';
  });
}

/**
 * IMP : Profile Input Field 설정
 * @param {*} validationState
 * @param {*} updateRegisterButtonState
 */
function setupProfileInput(validationState, updateRegisterButtonState) {
  const profileInput = document.getElementById('registerProfile');
  const previewImage = document.getElementById('previewImage');
  const profileHelper = document.getElementById('registerProfileHelper');

  profileInput.addEventListener('change', function () {
    if (profileInput.files.length > 0) {
      profileHelper.innerText = '';
      validationState.profile = true;
    } else {
      profileHelper.innerText = '* 프로필 사진을 추가해주세요.';
      validationState.profile = false;
    }
    updateRegisterButtonState();

    const file = profileInput.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => (previewImage.src = e.target.result);
      reader.readAsDataURL(file);
    }
  });
}

/**
 * IMP : Email Input Field 설정
 * @param {*} validationState
 * @param {*} updateRegisterButtonState
 */
function setupEmailInput(validationState, updateRegisterButtonState) {
  const emailInput = document.getElementById('email');
  const emailHelper = document.getElementById('emailHelper');

  emailInput.addEventListener('change', () => {
    let validResult = validateEmail(emailInput);
    emailHelper.innerText = validResult.valid ? '' : validResult.message;
    emailHelper.classList.toggle('hidden', validResult.valid);
    validationState.email = validResult.valid;
    updateRegisterButtonState();
  });
}

/**
 * IMP : Password Input Field 설정
 * @param {*} validationState
 * @param {*} updateRegisterButtonState
 */
function setupPasswordInput(validationState, updateRegisterButtonState) {
  const passwordInput = document.getElementById('password');
  const passwordHelper = document.getElementById('passwordHelper');

  passwordInput.addEventListener('change', () => {
    let validResult = validatePassword(passwordInput);
    passwordHelper.innerText = validResult.valid ? '' : validResult.message;
    passwordHelper.classList.toggle('hidden', validResult.valid);
    validationState.password = validResult.valid;
    updateRegisterButtonState();
  });
}

/**
 * IMP : Confirm Password Input Field 설정
 * @param {*} validationState
 * @param {*} updateRegisterButtonState
 */
function setupConfirmPasswordInput(validationState, updateRegisterButtonState) {
  const passwordInput = document.getElementById('password');
  const confirmPasswordInput = document.getElementById('confirmPassword');
  const confirmPasswordHelper = document.getElementById('confirmPasswordHelper');

  confirmPasswordInput.addEventListener('change', () => {
    let validResult = validateConfirmPassword(passwordInput, confirmPasswordInput);
    confirmPasswordHelper.innerText = validResult.valid ? '' : validResult.message;
    confirmPasswordHelper.classList.toggle('hidden', validResult.valid);
    validationState.confirmPassword = validResult.valid;
    updateRegisterButtonState();
  });
}

/**
 * IMP : Nickname Input Field 설정
 * @param {*} validationState
 * @param {*} updateRegisterButtonState
 */
function setupNicknameInput(validationState, updateRegisterButtonState) {
  const nicknameInput = document.getElementById('nickname');
  const nicknameHelper = document.getElementById('nicknameHelper');

  nicknameInput.addEventListener('change', () => {
    let validResult = validateNickname(nicknameInput);
    nicknameHelper.innerText = validResult.valid ? '' : validResult.message;
    nicknameHelper.classList.toggle('hidden', validResult.valid);
    validationState.nickname = validResult.valid;
    updateRegisterButtonState();
  });
}
