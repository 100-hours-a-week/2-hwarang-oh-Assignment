const routes = {
  "/": "components/user/login.html",
  "/posts": "/components/posts/posts.html",
  "/posts/:id": "/components/posts/post-detail.html",
  "/posts/:id/edit": "/components/posts/post-edit.html",
  "/posts/new": "/components/posts/new-post.html",
  "/user/edit": "/components/user/user-edit.html",
  "/user/change-password": "/components/user/change-password.html",
  "/register": "/components/user/register.html",
};

async function loadComponent(targetId, file) {
  const target = document.getElementById(targetId);
  const response = await fetch(file);
  const content = await response.text();
  target.innerHTML = content;
}

async function loadContent() {
  const path = window.location.pathname;
  console.log(`현재 경로: ${path}`);

  let content = routes[path];
  if (/^\/posts\/\d+$/.test(path)) {
    content = routes["/community/posts/:id"];
  } else if (/^\/posts\/\d+\/edit$/.test(path)) {
    content = routes["/community/posts/:id/edit"];
  }

  // 404 처리
  if (!content) {
    content = "components/404.html";
  }

  console.log(`로딩할 콘텐츠: ${content}`);
  await loadComponent("currentContents", content);
}

document.addEventListener("DOMContentLoaded", async () => {
  await loadComponent("header", "/components/header.html");
  await loadComponent("content", "/components/content.html");
  loadContent();
});
