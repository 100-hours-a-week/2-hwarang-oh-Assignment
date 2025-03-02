import { getPosts, getUsers } from './util_database.js';

/**
 * IMP : Rendering Post List Page
 */
export async function renderPosts() {
  const postList = document.getElementById('postList');
  const template = document.getElementById('post-template');
  const posts = getPosts();
  const users = getUsers();

  document.getElementById('createPostButton').addEventListener('click', function () {
    window.location.href = '/posts/create';
  });

  // IMP : Post Template의 Slot에 데이터를 삽입한다.
  posts.forEach((post) => {
    const clone = document.importNode(template.content, true);
    const author = users.find((user) => user.id === post.authorId);

    clone.querySelector("slot[name='title']").textContent = post.title;
    clone.querySelector("slot[name='date']").textContent = post.date;
    clone.querySelector("slot[name='author']").textContent = author.nickname;
    clone.querySelector("slot[name='likes']").textContent = post.likes;
    clone.querySelector("slot[name='comments']").textContent = post.comments.length;
    clone.querySelector("slot[name='views']").textContent = post.views;
    clone.querySelector('.post-profile').src = author.profileImage;
    clone.querySelector('.post-card').addEventListener('click', function () {
      window.location.href = `/posts/${post.id}`;
    });
    postList.appendChild(clone);
  });
}
