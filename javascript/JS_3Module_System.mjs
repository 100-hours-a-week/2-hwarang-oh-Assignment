import { add, subtract } from "./math.mjs";
import myUser from "./profile.mjs";
/**
 * @module JS_3Module_System
 * IMP : mjs extension을 사용하지 않으면, import/export 구문을 사용할 수 없다.
 * ? Node.js는 기본적으로 CommonJS를 사용하므로, ES6의 import/export 구문을 사용하려면 .mjs 확장자를 사용해야 한다.
 */

let sum = add(2, 3);
console.log(sum); // 5

let diff = subtract(5, 2);
console.log(diff); // 3

const User = new myUser("ArimaKana", 18);
User.greet();
