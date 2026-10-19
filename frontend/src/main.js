import { createApp } from 'vue'
import { createPinia } from 'pinia'
import App from './App.vue'
import router from './router'
import { USE_MOCK } from './services/api'
import './assets/styles/main.css'

async function bootstrap() {
  // Mock 모드는 별도 어댑터를 주입할 뿐, 화면/서비스 코드는 동일하다. (npm run dev:mock)
  if (USE_MOCK) {
    const { installMock } = await import('./mocks/mockAdapter')
    installMock()
  }
  createApp(App).use(createPinia()).use(router).mount('#app')
}

bootstrap()
