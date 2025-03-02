import { getCurrentUser } from '../main.js';
import { createPost } from './util_database.js';
import { validateTitle } from './util_validator.js';
/**
 * IMP : Rendering Post Create Page
 */

export function renderPostCreate() {
  const postImage = document.getElementById('postImage');
  const postTitle = document.getElementById('postTitle');
  const postContent = document.getElementById('postContent');
  const postCreateButton = document.getElementById('postCreate');

  const newPost = {
    title: '',
    content: '',
    postImage: '',
  };

  const validationState = {
    title: false,
    content: false,
  };

  function updatePostCreateButtonState() {
    const allValid = Object.values(validationState).every(Boolean);
    postCreateButton.disabled = !allValid;
    postCreateButton.classList.toggle('active', allValid);
  }

  setUpPostTitleInput(validationState, updatePostCreateButtonState);
  setUpPostContentInput(validationState, updatePostCreateButtonState);
  postImage.addEventListener('change', function () {
    const file = postImage.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        newPost.postImage = e.target.result;
        document.getElementById('fileLabel').textContent = file.name;
      };
      reader.readAsDataURL(file);
    }
  });

  postCreateButton.addEventListener('click', async function () {
    newPost.title = postTitle.value.trim();
    newPost.content = postContent.value.trim();
    let createPostResponse = createPost(newPost, getCurrentUser());
    if (!createPostResponse.success) {
      alert('게시글 작성에 실패했습니다.');
      return;
    }
    window.location.href = '/posts';
  });
}

/**
 * IMP : Post Title Input Field 설정
 * @param {*} validationState
 * @param {*} updatePostCreateButtonState
 */
function setUpPostTitleInput(validationState, updatePostCreateButtonState) {
  const postTitle = document.getElementById('postTitle');
  const postTitleHelper = document.getElementById('postTitleHelper');
  postTitle.addEventListener('change', function () {
    let validResult = validateTitle(postTitle);
    postTitleHelper.innerText = validResult.valid ? '' : validResult.message;
    postTitleHelper.classList.toggle('hidden', validResult.valid);
    validationState.title = validResult.valid;
    updatePostCreateButtonState();
  });
}

/**
 * IMP : Post Content Input Field 설정
 * @param {*} validationState
 * @param {*} updatePostCreateButtonState
 */
function setUpPostContentInput(validationState, updatePostCreateButtonState) {
  const postContent = document.getElementById('postContent');
  postContent.addEventListener('change', function () {
    validationState.content = postContent.value.trim().length > 0;
    updatePostCreateButtonState();
  });
}
