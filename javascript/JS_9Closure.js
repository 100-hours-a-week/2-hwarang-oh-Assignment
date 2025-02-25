/**
 * IMP : 1번 Counter 함수
 * @returns
 */
let Counter = () => {
  let count = 0;
  return {
    increment: () => count++,
    decrement: () => count--,
    getCount: () => count,
  };
};

let counter = Counter();
console.log(counter.getCount()); // 0
counter.increment();
counter.increment();
console.log(counter.getCount()); // 2
counter.decrement();
console.log(counter.getCount()); // 1

/**
 * IMP : 2번 Message Maker 함수
 * @param {*} defaultMessage
 * @returns
 */
let MessageMaker = (defaultMessage) => {
  let message = defaultMessage;
  return {
    makeMessage: (plusMessage) => message + plusMessage,
  };
};

let messageMaker = MessageMaker("Hello, ");
console.log(messageMaker.makeMessage("Closure!")); // Hello, Closure!
