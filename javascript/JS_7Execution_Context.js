const message = "Hello, JavaScript!";

const showMessage = () => {
  // console.log(message);
  // * Reference Error -> showMessage() 함수 내부의 let message 변수는 Hoisting이 발생했음
  // * 하지만, TDZ(Temporal Dead Zone)에 빠져 Reference Error가 발생한다.
  // * 전역에 선언되어 있는 const message 변수는 Scope Chain에서 이미 내부의 let message를 찾아서
  // * Hello, JavaScript는 출력되지 않는다.
  let message = "Hello, ES6!";
  console.log(message); // Hello, ES6!
};

showMessage();

/**
 * IMP : Execution Context
 */
const color = "blue";
const firstLevel = () => {
  let color = "red";
  const secondLevel = () => {
    let color = "green";
    console.log(color); // green => secondLevel() 의 함수 Block Scope에서 color 변수를 찾아 출력
  };
  secondLevel();
  console.log(color); // red => firstLevel() 의 함수 Block Scope에서 color 변수를 찾아 출력
};
firstLevel();
console.log(color); // blue => 전역 Scope에서 color 변수를 찾아 출력
