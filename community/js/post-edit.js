export async function renderEditPost() {
  const postId = window.location.pathname.split("/")[2];
  const db = await fetch("/data/db.json").then((res) => res.json());
  const post = db.posts.find((post) => post.id === Number(postId));

  document.getElementById("postTitle").value = post.title;
  document.getElementById("postContent").value = post.content;
  document.getElementById("fileLabel").textContent = post.postImage;

  document.getElementById("postImage").addEventListener("change", function (event) {
    const fileName = event.target.files[0] ? event.target.files[0].name : "파일을 선택해주세요";
    document.getElementById("fileLabel").textContent = fileName;
  });
}
