import { removeDB } from './util_database.js';
import { getCurrentUser, removeCurrentUser } from '../main.js';

// IMP: Header Rendering 및 Event 처리
export function updateHeader() {
  const user = getCurrentUser();
  const currentPath = window.location.pathname;
  const backward = document.getElementById('backward');
  const profileImage = document.getElementById('profileImage');
  const dropdownMenu = document.getElementById('dropdownMenu');

  if (currentPath === '/' || currentPath === '/posts') backward.style.visibility = 'hidden';
  else backward.style.visibility = 'visible';

  if (user) {
    profileImage.src = user.profileImage;
    profileImage.style.visibility = 'visible';
  } else profileImage.style.visibility = 'hidden';

  profileImage.addEventListener('click', function (event) {
    event.stopPropagation();
    dropdownMenu.style.display = dropdownMenu.style.display === 'block' ? 'none' : 'block';
  });

  document.addEventListener('click', function (event) {
    if (!profileImage.contains(event.target) && !dropdownMenu.contains(event.target)) {
      dropdownMenu.style.display = 'none';
    }
  });

  const logoutButton = document.querySelector("#dropdownMenu a[href='/']");
  if (logoutButton) {
    logoutButton.addEventListener('click', function (event) {
      event.preventDefault();
      logout();
    });
  }
}

// IMP : 로그아웃 처리
function logout() {
  removeDB();
  removeCurrentUser();
  alert('로그아웃 되었습니다.');
  window.location.href = '/';
}
