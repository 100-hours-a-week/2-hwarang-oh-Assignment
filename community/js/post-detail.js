export async function renderPostDetail() {
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
  document.getElementById("editPost").addEventListener("click", function () {
    window.location.href = `/posts/${postId}/edit`;
  });

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

    // Template Slot에 데이터를 삽입한다.
    clone.querySelector("slot[name='author']").textContent = comment.author.name;
    clone.querySelector("slot[name='date']").textContent = comment.date;
    clone.querySelector("slot[name='content']").textContent = comment.content;
    clone.querySelector(".comment-profile").src = comment.author.profileImage;

    commentsList.appendChild(clone);
  });
}
