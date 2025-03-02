/**
 * IMP : DataBase 관련 함수들을 모아놓은 파일
 * @description : LocalStorage를 이용하여 데이터베이스를 구현
 */
const DB_STORAGE_KEY = 'KTBCOMMUNITY_DB';
export function setDB(data) {
  localStorage.setItem(DB_STORAGE_KEY, JSON.stringify(data));
}

export function getDB() {
  return JSON.parse(localStorage.getItem(DB_STORAGE_KEY)) || null;
}

export function removeDB() {
  localStorage.removeItem(DB_STORAGE_KEY);
}

/**
 * IMP : POST와 관련된 CRUD 함수들
 */
// TYPE : GET -> Post List
export function getPosts() {
  const db = getDB();
  return db.posts;
}

/**
 * TYPE : GET -> Post Detail
 * ? : postID -> Number, post.id -> String => 이러한 이유로 == 연산자 사용
 * @param {*} postId
 * @returns
 */
export function getPost(postId) {
  const db = getDB();
  return db.posts.find((post) => post.id == postId);
}

/**
 * TYPE : POST -> Create Post
 * @param {*} title
 * @param {*} content
 * @param {*} postImage
 * @param {*} currentUser
 */
export function createPost(postInput, currentUser) {
  const db = getDB();
  const posts = db.posts;
  const newId = posts.length > 0 ? Math.max(...posts.map((p) => p.id)) + 1 : 1;
  const newPost = {
    id: newId,
    title: postInput.title,
    likes: 0,
    views: 0,
    authorId: currentUser.id,
    date: formatDate(new Date().toISOString()),
    comments: [],
    postImage: postInput.postImage,
    content: postInput.content,
  };
  posts.push(newPost);
  setDB(db);
  return { success: true, message: '게시글이 등록되었습니다.' };
}

/**
 * TYPE : PATCH -> Update Post
 * ? : postID -> Number, post.id -> String => 이러한 이유로 == 연산자 사용
 * @param {*} postId
 * @param {*} updatedTitle
 * @param {*} updatedContent
 * @param {*} updatedPostImage
 */
export function updatePost(postId, updatedPostInput) {
  const db = getDB();
  const posts = db.posts;
  const index = posts.findIndex((post) => post.id == postId);
  posts[index].title = updatedPostInput.title;
  posts[index].content = updatedPostInput.content;
  posts[index].postImage = updatedPostInput.postImage;
  setDB(db);
  return { success: true, message: '게시글이 수정되었습니다.' };
}

/**
 * TYPE : DELETE -> Delete Post
 * @param {*} postId
 */
export function deletePost(postId) {
  const db = getDB();
  db.posts = db.posts.filter((post) => post.id != postId);
  db.comments = db.comments.filter((comment) => comment.postId != postId);
  setDB(db);
}

/**
 * IMP : Comment와 관련된 CRUD 함수들
 * ? postID -> Number, post.id -> String => 이러한 이유로 == 연산자 사용
 */
// TYPE : GET -> Comment List for Each Post
export function getComments(postId) {
  const db = getDB();
  return db.comments.filter((comment) => comment.postId == postId);
}

/**
 * TYPE : POST -> Create Comment for Each Post
 * @param {*} postId
 * @param {*} commentContent
 * @param {*} currentUser
 */
export function createComment(postId, commentContent, currentUser) {
  const db = getDB();
  const comments = db.comments;
  const post = db.posts.find((post) => post.id == postId);
  const newId = comments.length > 0 ? Math.max(...comments.map((c) => c.id)) + 1 : 1;
  const newComment = {
    id: newId,
    postId,
    authorId: currentUser.id,
    content: commentContent,
    date: formatDate(new Date().toISOString()),
  };
  post.comments.push(newId);
  comments.push(newComment);
  setDB(db);
}

/**
 * TYPE : PATCH -> Update Comment for Each Post
 * @param {*} commentId
 * @param {*} updatedCommentContent
 */
export function updateComment(commentId, updatedCommentContent) {
  const db = getDB();
  const comments = db.comments;
  const index = comments.findIndex((comment) => comment.id === commentId);
  comments[index].content = updatedCommentContent;
  setDB(db);
}

/**
 * TYPE : DELETE -> Delete Comment for Each Post
 * @param {*} commentId
 */
export function deleteComment(commentId) {
  const db = getDB();
  const postId = db.comments.find((comment) => comment.id === commentId).postId;
  const post = db.posts.find((post) => post.id == postId);
  post.comments = post.comments.filter((comment) => comment !== commentId);
  db.comments = db.comments.filter((comment) => comment.id !== commentId);
  setDB(db);
}

/**
 * IMP : User와 관련된 CRUD 함수들
 */

// TYPE : GET -> User List
export function getUsers() {
  const db = getDB();
  return db.users;
}

/**
 * TYPE : POST -> Create User
 * @param {*} user
 * @returns
 */
export function registerUser(user) {
  const db = getDB();
  const users = db.users;
  if (users.find((eachUser) => eachUser.email === user.email)) {
    return { success: false, message: '이미 가입된 이메일입니다.' };
  }
  if (users.find((eachUser) => eachUser.nickname === user.nickname)) {
    return { success: false, message: '이미 사용 중인 닉네임입니다.' };
  }
  const newId = users.length > 0 ? Math.max(...users.map((u) => u.id)) + 1 : 1;
  const newUser = {
    id: newId,
    name: user.nickname,
    ...user,
  };

  users.push(newUser);
  setDB(db);
  return { success: true, message: '회원가입이 완료되었습니다!' };
}

/**
 * TYPE : PATCH -> Update User ( Profile, Password )
 * @param {*} updatedUser
 */
export function updateUser(updatedUser) {
  const db = getDB();
  const users = db.users;
  const index = users.findIndex((user) => user.id === updatedUser.id);
  users[index] = updatedUser;
  setDB(db);
}

/**
 * TYPE : DELETE -> Delete User & Related Data
 * @param {*} userId
 */
export function deleteUser(userId) {
  const db = getDB();
  // 사용자가 작성한 모든 게시글 ID 가져오기
  const userPostIds = db.posts.filter((post) => post.authorId === userId).map((post) => post.id);

  db.posts = db.posts.filter((post) => post.authorId !== userId);
  db.comments = db.comments.filter((comment) => !userPostIds.includes(comment.postId));
  db.comments = db.comments.filter((comment) => comment.authorId !== userId);
  db.users = db.users.filter((user) => user.id !== userId);
  setDB(db);
}

function formatDate(dateString) {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  const hours = String(date.getHours()).padStart(2, '0');
  const minutes = String(date.getMinutes()).padStart(2, '0');
  const seconds = String(date.getSeconds()).padStart(2, '0');

  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
}
