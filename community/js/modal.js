/**
 * @param {*} options
 * @param options.type : post, comment, user
 * @param options.id : post, comment, user id
 * @param options.onDelete : 삭제 콜백 함수
 * @returns
 */
export function showDeleteConfirmModal(options = {}) {
  let title, message;

  switch (options.type) {
    case 'post':
      title = '게시글을 삭제하시겠습니까?';
      message = '삭제한 내용은 복구할 수 없습니다.';
      break;
    case 'comment':
      title = '댓글을 삭제하시겠습니까?';
      message = '삭제한 내용은 복구할 수 없습니다.';
      break;
    case 'user':
      title = '회원탈퇴 하시겠습니까?';
      message = '회원탈퇴 시, 복구할 수 없습니다.';
      break;
    default:
      title = '삭제하시겠습니까?';
      message = '삭제한 내용은 복구할 수 없습니다.';
  }

  return showModal({
    title: title,
    message: message,
    cancelText: '취소',
    confirmText: '확인',
    onConfirm: () => {
      // 항목 삭제 로그 출력
      console.log(`${options.type} ${options.id} 삭제 요청`);

      // 삭제 콜백 실행
      if (options.onDelete) options.onDelete();
    },
  });
}

async function showModal(options = {}) {
  const response = await fetch('/components/modal.html');
  const html = await response.text();

  const tempContainer = document.createElement('div');
  tempContainer.innerHTML = html;
  const modal = tempContainer.firstElementChild;

  // 옵션 적용
  if (options.title) modal.querySelector('#modalTitle').textContent = options.title;
  if (options.message) modal.querySelector('#modalMessage').textContent = options.message;
  if (options.cancelText) modal.querySelector('#modalCancelBtn').textContent = options.cancelText;
  if (options.confirmText)
    modal.querySelector('#modalConfirmBtn').textContent = options.confirmText;

  // 모달을 body에 추가
  document.body.appendChild(modal);
  setupModalEvents(modal, options);
  setTimeout(() => {
    modal.classList.add('show');
  }, 10);
  return modal;
}

function setupModalEvents(modal, options) {
  const cancelBtn = modal.querySelector('#modalCancelBtn');
  const confirmBtn = modal.querySelector('#modalConfirmBtn');
  const backdrop = modal.querySelector('.modal-backdrop');

  // 취소 버튼 이벤트
  cancelBtn.addEventListener('click', () => {
    if (options.onCancel) options.onCancel();
    hideModal(modal);
  });

  // 확인 버튼 이벤트
  confirmBtn.addEventListener('click', () => {
    if (options.onConfirm) options.onConfirm();
    hideModal(modal);
  });

  // 백드롭 클릭 이벤트
  backdrop.addEventListener('click', () => {
    if (options.onCancel) options.onCancel();
    hideModal(modal);
  });
}

function hideModal(modal) {
  if (!modal) return;
  modal.classList.remove('show');
  setTimeout(() => {
    if (modal?.parentNode) modal.parentNode.removeChild(modal);
  }, 300);
}
