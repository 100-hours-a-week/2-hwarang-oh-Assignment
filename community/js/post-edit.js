import { getPost, updatePost } from './util_database.js';

/**
 * IMP : Rendering Edit Page
 */
export function renderEditPost() {
  const postId = window.location.pathname.split('/')[2];
  const post = getPost(postId);

  const postTitle = document.getElementById('postTitle');
  const postContent = document.getElementById('postContent');
  const postImage = document.getElementById('postImage');
  const fileLabel = document.getElementById('fileLabel');
  const postEditButton = document.getElementById('postEdit');

  postTitle.value = post.title;
  postContent.value = post.content;
  fileLabel.textContent = post.postImage ? '기존 이미지 유지' : '파일을 선택해주세요';

  postImage.addEventListener('change', function () {
    const file = postImage.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = (e) => {
        post.postImage = e.target.result;
        fileLabel.textContent = file.name;
      };
      reader.readAsDataURL(file);
    }
  });

  postEditButton.addEventListener('click', async function () {
    post.title = postTitle.value.trim();
    post.content = postContent.value.trim();
    let updatePostResponse = updatePost(postId, post);
    if (!updatePostResponse.success) {
      alert('게시글 수정에 실패했습니다.');
      return;
    }
    window.location.href = `/posts/${postId}`;
  });
}
