const routes = {
  "/community/": "components/user/login.html",
  "/community/posts": "components/posts/posts.html",
  "/community/posts/:id": "components/posts/post-detail.html",
  "/community/posts/:id/edit": "components/posts/post-edit.html",
  "/community/posts/new": "components/posts/new-post.html",
  "/community/user/edit": "components/user/user.html",
  "/community/user/change-password": "components/user/change-password.html",
  "/community/register": "components/user/register.html",
};

async function loadComponent(targetId, file) {
  const target = document.getElementById(targetId);
  const response = await fetch(file);
  const content = await response.text();
  target.innerHTML = content;
}

async function loadContent() {
  const path = window.location.pathname;
  console.log(path);
  let content = routes[path] || "components/404.html";

  // 게시글 상세 (`/posts/:id`)
  if (/^\/posts\/\d+$/.test(path)) {
    content = "components/posts/post-detail.html";
  }
  // 게시글 수정 (`/posts/:id/edit`)
  else if (/^\/posts\/\d+\/edit$/.test(path)) {
    content = "components/posts/post-edit.html";
  }
  console.log(content);

  await loadComponent("currentContents", content);
}

document.addEventListener("DOMContentLoaded", async () => {
  loadComponent("header", "components/header.html");
  await loadComponent("content", "components/content.html");
  loadContent();
});
