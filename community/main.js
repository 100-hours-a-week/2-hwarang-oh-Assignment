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

document.addEventListener("DOMContentLoaded", async () => {
  await loadComponent("header", "/components/header.html");
  await loadComponent("content", "/components/content.html");
  loadContent();
});

async function loadContent() {
  const path = window.location.pathname;
  console.log(`현재 경로: ${path}`);

  let content = routes[path];
  if (/^\/posts\/\d+$/.test(path)) {
    content = routes["/posts/:id"];
  } else if (/^\/posts\/\d+\/edit$/.test(path)) {
    content = routes["/posts/:id/edit"];
  }

  // 404 처리
  if (!content) {
    content = "components/404.html";
  }

  console.log(`로딩할 콘텐츠: ${content}`);
  await loadComponent("currentContents", content);
  if (path === "/posts") {
    renderPosts();
  } else if (/^\/posts\/\d+$/.test(path)) {
    renderPostDetail(); // 동적 게시글 상세
  } else if (/^\/posts\/\d+\/edit$/.test(path)) {
    renderEditPost(); // 게시글 수정
  }
}

async function loadComponent(targetId, file) {
  const target = document.getElementById(targetId);
  const response = await fetch(file);
  const content = await response.text();
  target.innerHTML = content;
}

async function renderPosts() {
  const postList = document.getElementById("postList");
  const template = document.getElementById("post-template");
  const db = await fetch("data/db.json").then((res) => res.json());
  const posts = db.posts;
  const users = db.users;

  posts.forEach((post) => {
    const clone = document.importNode(template.content, true);
    const author = users.find((user) => user.id === post.authorId);

    clone.querySelector("slot[name='title']").textContent = post.title;
    clone.querySelector("slot[name='date']").textContent = post.date;
    clone.querySelector("slot[name='author']").textContent = author.name;
    clone.querySelector("slot[name='likes']").textContent = post.likes;
    clone.querySelector("slot[name='comments']").textContent = post.comments.length;
    clone.querySelector("slot[name='views']").textContent = post.views;
    clone.querySelector(".post-profile").src = author.profileImage;
    clone.querySelector(".post-card").addEventListener("click", function () {
      window.location.href = `/posts/${post.id}`;
    });
    postList.appendChild(clone);
  });
}

async function renderPostDetail() {
  const postId = window.location.pathname.split("/")[2];
  const db = await fetch("/data/db.json").then((res) => res.json());
  const post = db.posts.find((post) => post.id === Number(postId));
  const author = db.users.find((user) => user.id === post.authorId);

  document.getElementById("postTitle").textContent = post.title;
  document.getElementById("postAuthor").textContent = author.name;
  document.getElementById("postDate").textContent = post.date;
  document.getElementById("postProfileImage").src = author.profileImage;
  document.getElementById("postImage").src = post.postImage;
  document.getElementById("postContent").textContent = post.content;
  document.getElementById("postLikes").textContent = post.likes;
  document.getElementById("postViews").textContent = post.views;
  document.getElementById("postCommentsCount").textContent = post.comments.length;

  const comments = db.comments
    .filter((comment) => post.comments.includes(comment.id))
    .map((comment) => ({
      ...comment,
      author: db.users.find((user) => user.id === comment.authorId),
    }));
  renderComments(comments);
}

function renderComments(comments) {
  const commentsList = document.getElementById("commentsList");
  const template = document.getElementById("comment-template");

  if (comments.length === 0) {
    commentsList.innerHTML = "<p class='no-comments'>등록된 댓글이 없습니다.</p>";
    return;
  }

  comments.forEach((comment) => {
    const clone = document.importNode(template.content, true);

    // 슬롯에 데이터 삽입
    clone.querySelector("slot[name='author']").textContent = comment.author.name;
    clone.querySelector("slot[name='date']").textContent = comment.date;
    clone.querySelector("slot[name='content']").textContent = comment.content;
    clone.querySelector(".comment-profile").src = comment.author.profileImage;

    commentsList.appendChild(clone);
  });
}
