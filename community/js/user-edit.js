import { showDeleteConfirmModal } from './modal.js';
import { setCurrentUser, getCurrentUser } from '../main.js';
import { updateUser, deleteUser } from './util_database.js';
import { validateNickname, validatePassword, validateConfirmPassword } from './util_validator.js';

/**
 * IMP : Rendering User Edit Page
 */
export function renderUserEditPage() {
  const nicknameInput = document.getElementById('nickname');
  const toastMessage = document.getElementById('toastMessage');
  const previewImage = document.getElementById('previewImage');
  const userEditButton = document.getElementById('userEditButton');
  const userDeleteLink = document.querySelector('.user-delete-link');

  const user = getCurrentUser();
  const validationState = {
    profile: false,
    nickname: false,
  };
  function updateUserEditButtonState() {
    const userEditButton = document.getElementById('userEditButton');
    const anyValid = Object.values(validationState).some(Boolean); // 하나라도 true이면 활성화
    userEditButton.disabled = !anyValid;
    userEditButton.classList.toggle('active', anyValid);
  }
  setupUserEdit(validationState, updateUserEditButtonState, user);

  // * User 정보 수정 버튼 클릭 시, Session Storage에 저장된 User 정보 업데이트
  userEditButton.addEventListener('click', function () {
    const updatedUser = {
      email: user.email,
      id: user.id,
      nickname: nicknameInput.value.trim(),
      password: user.password,
      profileImage: previewImage.src,
    };
    updateUser(updatedUser);
    setCurrentUser(updatedUser);
    toastMessage.classList.add('show');
    setTimeout(() => {
      toastMessage.classList.remove('show');
      window.location.href = '/posts'; // 홈으로 리디렉션
    }, 1000);
  });

  userDeleteLink.addEventListener('click', function () {
    showDeleteConfirmModal({
      type: 'user',
      id: user.id,
      onDelete: () => {
        deleteUser(user.id);
        setCurrentUser(null);
        window.location.href = '/';
      },
    });
  });
}

function setupUserEdit(validationState, updateUserEditButtonState, user) {
  const emailInput = document.getElementById('email');
  const nicknameInput = document.getElementById('nickname');
  const editImageInput = document.getElementById('editImage');
  const previewImage = document.getElementById('previewImage');
  const nicknameHelper = document.getElementById('nicknameHelper');

  emailInput.value = user.email;
  nicknameInput.value = user.nickname;
  previewImage.src = user.profileImage || '/image/placeholder.webp';

  nicknameInput.addEventListener('change', () => {
    let validResult = validateNickname(nicknameInput);
    nicknameHelper.innerText = validResult.valid ? '' : validResult.message;
    nicknameHelper.classList.toggle('hidden', validResult.valid);
    validationState.nickname = validResult.valid && nicknameInput.value !== user.nickname;
    updateUserEditButtonState();
  });

  editImageInput.addEventListener('change', function () {
    if (editImageInput.files.length > 0) {
      validationState.profile = true;
    } else {
      validationState.profile = false;
    }
    updateUserEditButtonState();

    const file = editImageInput.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => (previewImage.src = e.target.result);
      reader.readAsDataURL(file);
    }
  });
}

/**
 * IMP : Rendering Password Change Page
 */
export function renderPasswordChangePage() {
  const passwordInput = document.getElementById('password');
  const confirmPasswordInput = document.getElementById('confirmPassword');
  const passwordChangeButton = document.getElementById('passwordChangeButton');
  const passwordHelper = document.getElementById('passwordHelper');
  const confirmPasswordHelper = document.getElementById('confirmPasswordHelper');
  const toastMessage = document.getElementById('toastMessage');

  const user = getCurrentUser();
  const validationState = {
    password: false,
    confirmPassword: false,
  };

  function updatePasswordChangeButton() {
    const allValid = Object.values(validationState).every(Boolean);
    passwordChangeButton.disabled = !allValid;
    passwordChangeButton.classList.toggle('active', allValid);
  }

  passwordInput.addEventListener('change', () => {
    let validResult = validatePassword(passwordInput);
    passwordHelper.innerText = validResult.valid ? '' : validResult.message;
    passwordHelper.classList.toggle('hidden', validResult.valid);
    validationState.password = validResult.valid;
    updatePasswordChangeButton();
  });

  confirmPasswordInput.addEventListener('change', () => {
    let validResult = validateConfirmPassword(passwordInput, confirmPasswordInput);
    confirmPasswordHelper.innerText = validResult.valid ? '' : validResult.message;
    confirmPasswordHelper.classList.toggle('hidden', validResult.valid);
    validationState.confirmPassword = validResult.valid;
    updatePasswordChangeButton();
  });

  // * 비밀번호 변경 버튼 클릭 시, Session Storage에 저장된 User 정보 업데이트
  passwordChangeButton.addEventListener('click', function () {
    const newPassword = passwordInput.value.trim();

    user.password = newPassword;
    setCurrentUser(user);
    updateUser(user);

    toastMessage.classList.add('show');
    setTimeout(() => {
      toastMessage.classList.remove('show');
      window.location.href = '/posts';
    }, 1000);
  });
}
