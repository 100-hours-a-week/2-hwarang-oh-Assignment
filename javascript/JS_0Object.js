let myPet = {
  name: "Momo",
  type: "Cat",
};

console.log(myPet.name); // Momo
console.log(myPet.type); // Cat

class Person {
  constructor(name, age) {
    this.name = name;
    this.age = age;
  }
  greet() {
    console.log("Hello, my name is " + this.name + " and I am " + this.age + " years old.");
  }
}
new Person("John Doe", 25).greet(); // Hello, my name is John Doe and I am 25 years old.
