// IMP : Post 기능 관련 Import
import { renderPosts } from './js/posts.js';
import { renderPostDetail } from './js/post-detail.js';
import { renderEditPost } from './js/post-edit.js';

// IMP : User 기능 관련 Import
import { renderLoginPage } from './js/login.js';
import { renderRegister } from './js/register.js';
import { renderUserEditPage, renderPasswordChangePage } from './js/user-edit.js';

// IMP : Header 기능 Import
import { updateHeader } from './js/header.js';

// IMP : Routing
const routes = {
  '/': { path: 'components/user/login.html', render: () => renderLoginPage() },
  '/posts': { path: '/components/posts/posts.html', render: renderPosts },
  '/posts/:id': { path: '/components/posts/post-detail.html', render: renderPostDetail },
  '/posts/:id/edit': { path: '/components/posts/post-edit.html', render: renderEditPost },
  '/posts/create': { path: '/components/posts/post-create.html' },
  '/user/edit': { path: '/components/user/user-edit.html', render: renderUserEditPage },
  '/user/change-password': {
    path: '/components/user/change-password.html',
    render: renderPasswordChangePage,
  },
  '/register': { path: '/components/user/register.html', render: renderRegister },
};

// IMP : Get/Set/Remove Current User
const USER_STORAGE_KEY = 'currentUser';
export function getCurrentUser() {
  return JSON.parse(sessionStorage.getItem(USER_STORAGE_KEY));
}

export function setCurrentUser(user) {
  sessionStorage.setItem(USER_STORAGE_KEY, JSON.stringify(user));
}

export function removeCurrentUser() {
  sessionStorage.removeItem(USER_STORAGE_KEY);
}

// IMP : Route 된 Page Loading
document.addEventListener('DOMContentLoaded', async () => {
  await loadComponent('header', '/components/header.html');
  await loadComponent('content', '/components/content.html');
  updateHeader();
  loadContent();
});

// IMP : Component Loading => URL에 맞는 HTML 파일을 가져와서 해당 Target에 삽입
async function loadComponent(targetId, file) {
  const target = document.getElementById(targetId);
  const response = await fetch(file);
  const content = await response.text();
  target.innerHTML = content;
}

// IMP : Component Loading 이후, 각 HTML Tag에 맞는 Content Loading
async function loadContent() {
  let path = window.location.pathname;
  console.log(`로딩할 경로: ${path}`);

  if (/^\/posts\/\d+$/.test(path)) path = '/posts/:id';
  else if (/^\/posts\/\d+\/edit$/.test(path)) path = '/posts/:id/edit';
  let route = routes[path] || { path: 'components/404.html' };
  console.log(`로딩할 콘텐츠: ${route.path}`);

  // TYPE : 로그인 상태에서는 / 경로로 접근 시, /posts로 Redirect
  if (path === '/' && getCurrentUser()) {
    window.location.href = '/posts';
  }

  // * 1. 먼저 URL Path에 맞는 Component를 Loading한다.
  await loadComponent('currentContents', route.path);

  // * 2. 로딩된 Component에 맞는 Page를 Rendering한다.
  if (route.render) route.render();
}
