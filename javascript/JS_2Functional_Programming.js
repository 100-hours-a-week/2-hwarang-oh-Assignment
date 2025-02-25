const add = (a, b) => a + b;
const sumArray = (arr) => {
  let total = 0;
  for (const num of arr) total += num;
  return total;
};
const sumArray2 = (arr) => arr.reduce(add, 0);

let sum = add(1, 2);
console.log(sum); // 3

let total = sumArray([1, 2, 3, 4, 5]);
let total2 = sumArray2([1, 2, 3, 4, 5]);
console.log(total); // 15
console.log(total2); // 15
