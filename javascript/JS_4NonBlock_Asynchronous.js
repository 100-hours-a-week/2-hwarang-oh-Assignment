// Normal
console.log("Start");
console.log("Processing");
console.log("End");

// Non-blocking Asynchronous
console.log("Start");
setTimeout(() => {
  console.log("Async Operation Complete");
}, 1000);
console.log("End");
