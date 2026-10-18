import api from './api'

// API 명세서 1차 확정 — @RequestMapping("/api/qna")
export const qnaService = {
  list: (params) => api.get('/qna', { params }).then((r) => r.data), // Q&A 목록
  get: (id) => api.get(`/qna/${id}`).then((r) => r.data), // Q&A 상세 보기
  mine: () => api.get('/qna/my').then((r) => r.data), // 마이 Q&A
  create: (payload) => api.post('/qna', payload).then((r) => r.data), // QnaRequest { title, content }
  update: (id, payload) => api.put(`/qna/${id}`, payload).then((r) => r.data), // 관리자 || 작성자
  remove: (id) => api.delete(`/qna/${id}`).then((r) => r.data), // 관리자 || 작성자

  // ---- 관리자 ----
  waiting: (params) => api.get('/qna/admin', { params }).then((r) => r.data), // 답변 대기 중인 Q&A 목록
  answer: (id, answer) => api.post(`/qna/admin/${id}`, { answer }).then((r) => r.data), // 답변 등록
  updateAnswer: (id, answer) => api.put(`/qna/admin/${id}`, { answer }).then((r) => r.data), // 답변 수정
  removeAnswer: (id) => api.delete(`/qna/admin/${id}`).then((r) => r.data), // 답변 삭제
}
