const myFirstPromise = new Promise((resolve, reject) => {
  resolve("Hello, Promise!");
});
myFirstPromise.then((message) => console.log(message));

async function waitForMessage() {
  const message = await new Promise((resolve, reject) => {
    setTimeout(resolve("Hello, Async/Await!"), 1000);
  });
  return message;
}
waitForMessage().then((message) => console.log(message));
