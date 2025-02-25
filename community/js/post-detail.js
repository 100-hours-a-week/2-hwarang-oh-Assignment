import { showDeleteConfirmModal } from "./modal.js";
import { getCurrentUser } from "../main.js";

/**
 * IMP : Rendering Post Detail Page
 */
export async function renderPostDetail() {
  const postId = window.location.pathname.split("/")[2];
  const db = await fetch("/data/db.json").then((res) => res.json());
  const post = db.posts.find((post) => post.id === Number(postId));
  const author = db.users.find((user) => user.id === post.authorId);
  const currentUser = getCurrentUser();
  const comments = db.comments
    .filter((comment) => post.comments.includes(comment.id))
    .map((comment) => ({
      ...comment,
      author: db.users.find((user) => user.id === comment.authorId),
    }));

  renderPostContents(author, post);
  renderPostButton(currentUser, post);
  renderComments(currentUser, comments);
}

/**
 * IMP : render Post Contents
 * @param {*} author
 * @param {*} post
 */
function renderPostContents(author, post) {
  document.getElementById("postTitle").textContent = post.title;
  document.getElementById("postAuthor").textContent = post.author;
  document.getElementById("postDate").textContent = post.date;
  document.getElementById("postProfileImage").src = author.profileImage;
  document.getElementById("postImage").src = post.postImage;
  document.getElementById("postContent").textContent = post.content;
  document.getElementById("postLikes").textContent = post.likes;
  document.getElementById("postViews").textContent = post.views;
  document.getElementById("postCommentsCount").textContent = post.comments.length;
}

/**
 * IMP : render Post Button
 * @param {*} currentUser
 * @param {*} post
 */
function renderPostButton(currentUser, post) {
  const editPostBtn = document.getElementById("editPost");
  const deletePostBtn = document.getElementById("deletePost");

  if (currentUser && currentUser.id === post.authorId) {
    editPostBtn.style.display = "block";
    deletePostBtn.style.display = "block";

    editPostBtn.addEventListener("click", function () {
      window.location.href = `/posts/${post.id}/edit`;
    });
    deletePostBtn.addEventListener("click", function () {
      showDeleteConfirmModal({
        type: "post",
        id: post.id,
        onDelete: () => {
          window.location.href = "/posts";
        },
      });
    });
  } else {
    editPostBtn.style.display = "none";
    deletePostBtn.style.display = "none";
  }
}

/**
 * IMP : render Comment List
 * @param {*} comments
 * @returns
 */
function renderComments(currentUser, comments) {
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

    const commentButtons = clone.querySelector(".comment-buttons");
    const editButton = clone.querySelector(".edit-comment");
    const deleteButton = clone.querySelector(".delete-comment");

    // 각 버튼에 고유 ID 부여 (commentId를 활용)
    editButton.id = `edit-comment-${comment.id}`;
    deleteButton.id = `delete-comment-${comment.id}`;
    if (currentUser && currentUser.id === comment.authorId) {
      commentButtons.style.display = "flex";
      // TODO 수정 버튼 이벤트 리스너
      // editButton.addEventListener("click", function () {
      //   handleCommentEdit(comment.id);
      // });

      // TODO : 삭제 버튼 이벤트 리스너
      deleteButton.addEventListener("click", function () {
        showDeleteConfirmModal({ type: "comment", id: comment.id });
      });
    } else {
      commentButtons.style.display = "none";
    }
    commentsList.appendChild(clone);
  });
}
