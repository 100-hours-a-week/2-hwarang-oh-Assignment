// IMP : Validation Check

// TYPE : User Validation
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

export function validateConfirmPassword(passwordInput, confirmPasswordInput) {
  const password = passwordInput.value.trim();
  const confirmPassword = confirmPasswordInput.value.trim();

  if (!confirmPassword) {
    return { valid: false, message: '* 비밀번호를 다시 입력해주세요.' };
  }
  if (password !== confirmPassword) {
    return { valid: false, message: '* 비밀번호가 일치하지 않습니다.' };
  }
  return { valid: true, message: '' };
}

export function validateNickname(input) {
  const value = input.value.trim();
  const hasWhitespace = /\s/.test(value);
  const isTooLong = value.length > 10;

  if (!value) {
    return { valid: false, message: '* 닉네임을 입력해주세요.' };
  }
  if (hasWhitespace) {
    return { valid: false, message: '* 띄어쓰기를 없애주세요.' };
  }
  if (isTooLong) {
    return { valid: false, message: '* 닉네임은 최대 10자까지 작성 가능합니다.' };
  }

  return { valid: true, message: '' };
}

// TYPE : Post Validation
export function validateTitle(input) {
  const value = input.value.trim();
  if (!value) {
    return { valid: false, message: '* 제목을 입력해주세요.' };
  }
  if (value.length >= 27) {
    return { valid: false, message: '* 제목은 26자 이내로 작성해주세요.' };
  }
  return { valid: true, message: '' };
}
