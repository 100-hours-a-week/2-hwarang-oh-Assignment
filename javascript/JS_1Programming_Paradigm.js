let list = [1, 2, 3, 4, 5];

// reduce() method => reduce the array to a single value
let sum = list.reduce((acc, current) => acc + current, 0);
console.log(sum); // 15

// map() method => create a new array by current array
let doubled = list.map((current) => current * 2);
console.log(doubled); // [2, 4, 6, 8, 10]
