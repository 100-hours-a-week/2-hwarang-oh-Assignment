import { getCurrentUser } from '../main.js';
import { showDeleteConfirmModal } from './modal.js';
import {
  getComments,
  getPost,
  getUsers,
  deletePost,
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
  const currentUser = getCurrentUser();
  const author = users.find((user) => user.id === post.authorId);
  const comments = getComments(postId).map((comment) => ({
    ...comment,
    author: users.find((user) => user.id === comment.authorId),
  }));

  // TYPE : Post Detail Page Rendering
  renderPostContents(author, post);

  // TYPE : Post Related Button Rendering
  renderPostButton(currentUser, post);

  // TYPE : Comment List Rendering
  renderComments(currentUser, comments);
}

/**
 * IMP : Post Detail Page Rendering
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
 * IMP : Post Related Button Rendering
 * @param {*} currentUser
 * @param {*} post
 */
function renderPostButton(currentUser, post) {
  const editPostBtn = document.getElementById('editPost');
  const deletePostBtn = document.getElementById('deletePost');

  // ? : 현재 사용자가 게시글 작성자인 경우, 수정, 삭제 버튼 활성화
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
          deletePost(post.id);
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

    // IMP : Template Slot에 데이터를 삽입한다.
    contentElement.textContent = comment.content;
    clone.querySelector("slot[name='date']").textContent = comment.date;
    clone.querySelector('.comment-profile').src = comment.author.profileImage;
    clone.querySelector("slot[name='author']").textContent = comment.author.nickname;

    // IMP : 댓글 작성자가, 현재 사용자인 경우, 수정, 삭제 버튼 활성화
    const editForm = clone.querySelector('.comment-edit-form');
    const editInput = clone.querySelector('.edit-comment-input');
    const editCancelButton = clone.querySelector('.edit-comment-cancel');
    const editSaveButton = clone.querySelector('.edit-comment-submit');
    editForm.style.display = 'none';

    // IMP : 댓글 작성자 구분을 위해, 각 버튼에 고유 ID 부여 (commentId를 활용)
    editButton.id = `edit-comment-${comment.id}`;
    deleteButton.id = `delete-comment-${comment.id}`;
    if (currentUser && currentUser.id === comment.authorId) {
      commentButtons.style.display = 'flex';

      // TYPE : 수정 Button -> 클릭 시, 수정 Form 활성화
      editButton.addEventListener('click', function () {
        enterEditMode(contentElement, editForm, editInput, comment);
      });

      // TYPE : 삭제 Button -> 클릭 시, Modal 띄우기 => CallBack : deleteComment()
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

      // TYPE : 수정 Form -> Cancel Button -> 클릭 시, Form 비활성화
      editCancelButton.addEventListener('click', function () {
        contentElement.style.display = 'block';
        editForm.style.display = 'none';
      });

      // TYPE : 수정 Form -> Save Button -> 클릭 시, 수정 & Form 비활성화
      editSaveButton.addEventListener('click', function () {
        saveCommentEdit(contentElement, editForm, editInput, comment);
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
