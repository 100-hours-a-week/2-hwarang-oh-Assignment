package com.ktb.automessage.track;

/**
 * IMP : 한글로 된 트랙 이름에 대한 Enum
 * * 풀스택 -> Full_Stack
 * * 인공지능 -> AI
 * * 클라우드 -> Cloud
 * ! 문제는 오탈자에 대해 대응하지 못한다는 것이다.
 * TODO : korean Track name에 대한 오탈자에 대한 대응이 구현되면, Enum 사용
 */
public enum Track {
    Full_Stack, AI, Cloud;
}
