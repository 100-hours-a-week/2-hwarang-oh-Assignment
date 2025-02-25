/**
 * 모달 HTML을 로드하고 표시하는 함수
 * @param {Object} options - 모달 설정 옵션
 * @param {string} options.title - 모달 제목
 * @param {string} options.message - 모달 메시지
 * @param {string} options.cancelText - 취소 버튼 텍스트 (기본값: '취소')
 * @param {string} options.confirmText - 확인 버튼 텍스트 (기본값: '확인')
 * @param {Function} options.onCancel - 취소 버튼 클릭 시 실행할 콜백
 * @param {Function} options.onConfirm - 확인 버튼 클릭 시 실행할 콜백
 */
export async function showModal(options = {}) {
  try {
    // 이미 있는 모달 제거
    const existingModal = document.getElementById("modalContainer");
    if (existingModal) {
      document.body.removeChild(existingModal);
    }

    // 모달 HTML 로드
    const response = await fetch("/components/modal.html");
    if (!response.ok) {
      throw new Error("모달 HTML을 로드하는데 실패했습니다.");
    }

    const html = await response.text();

    // 임시 컨테이너 생성 및 HTML 삽입
    const tempContainer = document.createElement("div");
    tempContainer.innerHTML = html;

    // 모달 요소 가져오기
    const modal = tempContainer.firstElementChild;

    // 옵션 적용
    if (options.title) {
      const titleEl = modal.querySelector("#modalTitle");
      if (titleEl) titleEl.textContent = options.title;
    }

    if (options.message) {
      const messageEl = modal.querySelector("#modalMessage");
      if (messageEl) messageEl.textContent = options.message;
    }

    if (options.cancelText) {
      const cancelBtn = modal.querySelector("#modalCancelBtn");
      if (cancelBtn) cancelBtn.textContent = options.cancelText;
    }

    if (options.confirmText) {
      const confirmBtn = modal.querySelector("#modalConfirmBtn");
      if (confirmBtn) confirmBtn.textContent = options.confirmText;
    }

    // 모달을 body에 추가
    document.body.appendChild(modal);

    // 이벤트 리스너 설정
    setupModalEvents(modal, options);

    // 모달 표시 (애니메이션을 위한 setTimeout)
    setTimeout(() => {
      modal.classList.add("show");
    }, 10);

    return modal;
  } catch (error) {
    console.error("모달 로드 중 오류:", error);
    alert("모달을 표시하는데 문제가 발생했습니다.");
  }
}

/**
 * 모달에 이벤트 리스너를 설정하는 함수
 * @param {HTMLElement} modal - 모달 요소
 * @param {Object} options - 모달 옵션
 */
function setupModalEvents(modal, options) {
  const cancelBtn = modal.querySelector("#modalCancelBtn");
  const confirmBtn = modal.querySelector("#modalConfirmBtn");
  const backdrop = modal.querySelector(".modal-backdrop");

  // 취소 버튼 이벤트
  if (cancelBtn) {
    cancelBtn.addEventListener("click", () => {
      if (options.onCancel && typeof options.onCancel === "function") {
        options.onCancel();
      }
      hideModal(modal);
    });
  }

  // 확인 버튼 이벤트
  if (confirmBtn) {
    confirmBtn.addEventListener("click", () => {
      if (options.onConfirm && typeof options.onConfirm === "function") {
        options.onConfirm();
      }
      hideModal(modal);
    });
  }

  // 백드롭 클릭 이벤트
  if (backdrop) {
    backdrop.addEventListener("click", () => {
      if (options.onCancel && typeof options.onCancel === "function") {
        options.onCancel();
      }
      hideModal(modal);
    });
  }
}

/**
 * 모달을 숨기는 함수
 * @param {HTMLElement} modal - 모달 요소
 */
export function hideModal(modal) {
  if (!modal) return;

  modal.classList.remove("show");

  // 애니메이션 종료 후 모달 제거
  setTimeout(() => {
    if (modal && modal.parentNode) {
      modal.parentNode.removeChild(modal);
    }
  }, 300); // 트랜지션 시간과 일치해야 함
}

/**
 * 게시글 삭제 확인 모달을 표시하는 함수
 * @param {number} postId - 삭제할 게시글 ID
 * @param {Function} onDelete - 삭제 성공 시 실행할 콜백
 */
export function showDeleteConfirmModal(postId, onDelete) {
  return showModal({
    title: "게시글을 삭제하시겠습니까?",
    message: "삭제한 내용은 복구 할 수 없습니다.",
    cancelText: "취소",
    confirmText: "확인",
    onConfirm: async () => {
      try {
        // 여기에 실제 삭제 API 호출 코드 추가
        // const response = await fetch(`/api/posts/${postId}`, { method: 'DELETE' });

        console.log(`게시글 ${postId} 삭제 요청`);

        // 삭제 후 콜백 실행
        if (onDelete && typeof onDelete === "function") {
          onDelete();
        }
      } catch (error) {
        console.error("게시글 삭제 중 오류 발생:", error);
        alert("게시글 삭제 중 오류가 발생했습니다.");
      }
    },
  });
}
