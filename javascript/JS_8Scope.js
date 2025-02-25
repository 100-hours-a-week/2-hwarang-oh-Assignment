/**
 * IMP : let vs const
 * * let으로 선언한 변수는 재할당이 가능하다
 * * const로 선언한 변수는 재할당이 불가능하다
 */

let temperature = 25;
temperature = 30;
const MAX_TEMPARATURE = 35;
// MAX_TEMPARATURE = 40; // TypeError: Assignment to constant variable.

/**
 * IMP : let은 Block Scope를 가진다
 * * Block Scope를 가지기 때문에 중괄호 {} 내에서 선언된 let/const 변수는 Block Scope를 가짐
 * * 이러한 Scope의 한계를 해결하기 위해서는, 다양한 방법이 있다
 * * 1. let을 Block 밖에서 먼저 정의한다.
 * * 2. let을 var로 변경한다. ( var는 Function Scope를 가짐 )
 */
if (true) {
  let isRaining = false;
}
// console.log(isRaining); // Reference Error
