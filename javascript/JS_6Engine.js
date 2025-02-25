/**
 * ? JS Code가 웹 페이지에서 어떻게 실행되는지 간단하게 설명하세요".
 * * JS Engine이 과정에서 어떤 역할을 하는지 설명에 포함해야 한다.
 *
 * IMP : Anwser
 * * 웹 페이지에서 JS Code가 실행되는 과정은 다음과 같다.
 *
 * TYPE Parsing
 * * JS Engine이 코드를 Token 단위로 분석한다.
 * * Token 단위로 나뉜 Code에 대해 Node를 만들고, 이를 기반으로 AST를 만들어 낸다
 *
 * TYPE Bytecode 변환
 * * Ignition 인터프리터가 AST를 기반으로, ByteCode를 생성한다.
 * * 실행이 반복되는 Hot Code는 TurboFan JIT Compiler가 Machine 언어로 변환하여 최적화를 도모함
 *
 * TYPE Execution
 * * 이후에는 JS의 Call Stack에 따라 실행 Context가 생성되고, 실행된다.
 */

// console.log(messageLet); // Reference Error
let messageLet = "Hello with let!";

// console.log(messageConst); // Reference Error
let messageConst = "Hello with const!";

// console.log(greet()); // Reference Error
const greet = () => "Hello, Arrow Function!";

/**
 * IMP : let, const Hoisting
 * * let, const는 Hoisting이 발생하지만, TDZ(Temporal Dead Zone)에 빠져 Reference Error가 발생한다.
 *
 * IMP : Arrow Function Hoisting
 * * Arrow Function은 함수 표현식과 동일하게 Hoisting이 발생하긴 하지만, 변수 호이스팅이 발생함
 * * 이러한 이유로, const 변수에 대한 Hoisting이 발생하여 TDZ에 빠져 Reference Error가 발생한다.
 */
