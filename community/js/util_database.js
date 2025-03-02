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

// IMP : POST CRUD
export function getPosts() {
  const db = getDB();
  return db.posts;
}

// ? postID -> Number, post.id -> String => 이러한 이유로 == 연산자 사용
export function getPost(postId) {
  const db = getDB();
  return db.posts.find((post) => post.id == postId);
}

export function createPost() {}

// ? postID -> Number, post.id -> String => 이러한 이유로 == 연산자 사용
export function getComments(postId) {
  const db = getDB();
  return db.comments.filter((comment) => comment.postId == postId);
}

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
    date: new Date().toISOString(),
  };
  post.comments.push(newId);
  comments.push(newComment);
  setDB(db);
}

export function updateComment(commentId, updatedCommentContent) {
  const db = getDB();
  const comments = db.comments;
  const index = comments.findIndex((comment) => comment.id === commentId);
  comments[index].content = updatedCommentContent;
  setDB(db);
}

export function deleteComment(commentId) {
  const db = getDB();
  const postId = db.comments.find((comment) => comment.id === commentId).postId;
  const post = db.posts.find((post) => post.id == postId);
  post.comments = post.comments.filter((comment) => comment !== commentId);
  db.comments = db.comments.filter((comment) => comment.id !== commentId);
  setDB(db);
}

// IMP : USER CRUD
/**
 * TYPE : GET
 * @returns
 */
export function getUsers() {
  const db = getDB();
  return db.users;
}

/**
 * TYPE : POST
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
 * TYPE : PATCH
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
 * TYPE : DELETE
 * @param {*} userId
 */
export function deleteUser(userId) {
  const db = getDB();
  db.users = db.users.filter((user) => user.id !== userId);
  setDB(db);
}
