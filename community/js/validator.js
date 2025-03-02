// IMP : Validation Check

export function validateEmail(input) {
  const value = input.value.trim();
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!value) {
    return { valid: false, message: '* 이메일을 입력해주세요.' };
  } else if (!emailRegex.test(value)) {
    return {
      valid: false,
      message: '* 올바른 이메일 주소 형식을 입력해주세요. (예: example1@example.com)',
    };
  } else {
    return { valid: true, message: '' };
  }
}

export function validatePassword(input) {
  const value = input.value.trim();
  const lengthValid = value.length >= 8 && value.length <= 20;
  const containsRequiredChars = /(?=.*\d)(?=.*[@$!%*?&])/.test(value); // 숫자 & 특수문자 포함 검사

  if (!value) {
    return { valid: false, message: '* 비밀번호를 입력해주세요.' };
  }
  if (!lengthValid) {
    return {
      valid: false,
      message: '* 비밀번호는 8자 이상, 20자 이하여야 합니다.',
    };
  }
  if (!containsRequiredChars) {
    return {
      valid: false,
      message: '* 비밀번호에 숫자와 특수문자(@$!%*?&)가 최소 1개 포함되어야 합니다.',
    };
  }
  return { valid: true, message: '' };
}
