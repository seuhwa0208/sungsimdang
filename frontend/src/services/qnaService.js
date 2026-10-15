import api from './api'

// TODO(미확정): Q&A 경로/공개 범위는 PRD 9.3 의 "제안"이다.
export const qnaService = {
  list: (params) => api.get('/qna', { params }).then((r) => r.data),
  get: (id) => api.get(`/qna/${id}`).then((r) => r.data),
  create: (payload) => api.post('/qna', payload).then((r) => r.data), // QnaRequest { title, content }
  update: (id, payload) => api.put(`/qna/${id}`, payload).then((r) => r.data),
  answer: (id, answer) => api.post(`/qna/${id}/answer`, { answer }).then((r) => r.data), // QnaAnswerRequest
  updateAnswer: (id, answer) => api.put(`/qna/${id}/answer`, { answer }).then((r) => r.data),
  // TODO(미확정): "내가 쓴 글" 전용 API. 백엔드 QnaRepository.findByMemberId... 에 대응하는 경로가 필요하다.
  mine: () => api.get('/qna/me').then((r) => r.data),
}
