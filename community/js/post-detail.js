import { getCurrentUser } from '../main.js';
import { showDeleteConfirmModal } from './modal.js';
import {
  getComments,
  getPost,
  getUsers,
  createComment,
  updateComment,
  deleteComment,
} from './util_database.js';

/**
 * IMP : Rendering Post Detail Page
 */
export async function renderPostDetail() {
  const postId = window.location.pathname.split('/')[2];
  const users = getUsers();
  const post = await getPost(postId);
  console.log(post);
  const currentUser = getCurrentUser();
  const author = users.find((user) => user.id === post.authorId);
  const comments = getComments(postId).map((comment) => ({
    ...comment,
    author: users.find((user) => user.id === comment.authorId),
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
  document.getElementById('postTitle').textContent = post.title;
  document.getElementById('postAuthor').textContent = post.author;
  document.getElementById('postDate').textContent = post.date;
  document.getElementById('postProfileImage').src = author.profileImage;
  document.getElementById('postImage').src = post.postImage;
  document.getElementById('postContent').textContent = post.content;
  document.getElementById('postLikes').textContent = post.likes;
  document.getElementById('postViews').textContent = post.views;
  document.getElementById('postCommentsCount').textContent = post.comments.length;
  const submitCommentButton = document.getElementById('submitComment');
  const commentInput = document.getElementById('commentInput');
  submitCommentButton.addEventListener('click', function () {
    const commentContent = commentInput.value.trim();
    if (commentContent === '') {
      alert('댓글을 입력하세요.');
      return;
    }
    createComment(post.id, commentContent, getCurrentUser());
    window.location.reload();
  });
}

/**
 * IMP : render Post Button
 * @param {*} currentUser
 * @param {*} post
 */
function renderPostButton(currentUser, post) {
  const editPostBtn = document.getElementById('editPost');
  const deletePostBtn = document.getElementById('deletePost');

  if (currentUser && currentUser.id === post.authorId) {
    editPostBtn.style.display = 'block';
    deletePostBtn.style.display = 'block';

    editPostBtn.addEventListener('click', function () {
      window.location.href = `/posts/${post.id}/edit`;
    });
    deletePostBtn.addEventListener('click', function () {
      showDeleteConfirmModal({
        type: 'post',
        id: post.id,
        onDelete: () => {
          window.location.href = '/posts';
        },
      });
    });
  } else {
    editPostBtn.style.display = 'none';
    deletePostBtn.style.display = 'none';
  }
}

/**
 * IMP : render Comment List
 * @param {*} comments
 * @returns
 */
function renderComments(currentUser, comments) {
  const commentsList = document.getElementById('commentsList');
  const template = document.getElementById('comment-template');

  if (comments.length === 0) {
    commentsList.innerHTML = "<p class='no-comments'>등록된 댓글이 없습니다.</p>";
    return;
  }

  comments.forEach((comment) => {
    const clone = document.importNode(template.content, true);

    const editButton = clone.querySelector('.edit-comment');
    const deleteButton = clone.querySelector('.delete-comment');
    const commentButtons = clone.querySelector('.comment-buttons');
    const contentElement = clone.querySelector('.comment-content');

    // Template Slot에 데이터를 삽입한다.
    contentElement.textContent = comment.content;
    clone.querySelector("slot[name='date']").textContent = comment.date;
    clone.querySelector('.comment-profile').src = comment.author.profileImage;
    clone.querySelector("slot[name='author']").textContent = comment.author.nickname;

    const editForm = clone.querySelector('.comment-edit-form');
    const editInput = clone.querySelector('.edit-comment-input');
    const editCancelButton = clone.querySelector('.edit-comment-cancel');
    const editSaveButton = clone.querySelector('.edit-comment-submit');
    editForm.style.display = 'none';

    // 각 버튼에 고유 ID 부여 (commentId를 활용)
    editButton.id = `edit-comment-${comment.id}`;
    deleteButton.id = `delete-comment-${comment.id}`;
    if (currentUser && currentUser.id === comment.authorId) {
      commentButtons.style.display = 'flex';

      editButton.addEventListener('click', function () {
        enterEditMode(contentElement, editForm, editInput, comment);
      });

      editCancelButton.addEventListener('click', function () {
        contentElement.style.display = 'block';
        editForm.style.display = 'none';
      });

      editSaveButton.addEventListener('click', function () {
        saveCommentEdit(contentElement, editForm, editInput, comment);
      });

      // TODO : 삭제 버튼 이벤트 리스너
      deleteButton.addEventListener('click', function () {
        showDeleteConfirmModal({
          type: 'comment',
          id: comment.id,
          onDelete: () => {
            deleteComment(comment.id);
            window.location.reload();
          },
        });
      });
    } else {
      commentButtons.style.display = 'none';
    }
    commentsList.appendChild(clone);
  });
}

function enterEditMode(contentElement, editForm, editInput, comment) {
  editInput.value = comment.content;
  contentElement.style.display = 'none';
  editForm.style.display = 'flex';
}

function saveCommentEdit(contentElement, editForm, editInput, comment) {
  const updatedContent = editInput.value.trim();
  if (updatedContent === '') {
    alert('댓글을 입력하세요.');
    return;
  }
  updateComment(comment.id, updatedContent);
  window.location.reload();
}
