import { validateEmail, validatePassword } from './validator.js';
/**
 * IMP : DataBase의 User Data와 입력된 User Data를 비교하여 로그인 처리
 * @param {*} email
 * @param {*} password
 */

export function renderLoginPage(USER_STORAGE_KEY) {
  // TYPE 1: 로그인 Form Submit Event
  const loginForm = document.querySelector('.login-form');
  loginForm.addEventListener('submit', function (event) {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    login(USER_STORAGE_KEY, email, password);
  });

  // TYPE 2: 이메일 입력에 대한 Helper Text
  const emailInput = document.getElementById('email');
  const emailHelper = document.getElementById('emailHelper');
  emailInput.addEventListener('focusout', () => {
    let validResult = validateEmail(emailInput);
    if (validResult.valid) emailHelper.classList.add('hidden');
    else {
      emailHelper.classList.remove('hidden');
      emailHelper.innerText = validResult.message;
    }
  });

  // TYPE 3: 비밀번호 입력에 대한 Helper Text
  const passwordInput = document.getElementById('password');
  const passwordHelper = document.getElementById('passwordHelper');
  passwordInput.addEventListener('focusout', () => {
    let validResult = validatePassword(passwordInput);
    if (validResult.valid) passwordHelper.classList.add('hidden');
    else {
      passwordHelper.classList.remove('hidden');
      passwordHelper.innerText = validResult.message;
    }
  });
}

async function login(USER_STORAGE_KEY, email, password) {
  try {
    const db = await fetch('/data/db.json').then((res) => res.json());
    const users = db.users;
    const user = users.find((user) => user.email === email && user.password === password);

    if (user) {
      sessionStorage.setItem(USER_STORAGE_KEY, JSON.stringify(user));
      alert(`🚀 KTB Community에 오신 것을 환영합니다, ${user.name}님!`);
      window.location.href = '/posts';
    } else alert('🚧 이메일 또는 비밀번호가 일치하지 않습니다.');
  } catch (error) {
    console.error('로그인 오류', error);
    alert('로그인 중 오류가 발생했습니다.');
  }
}
