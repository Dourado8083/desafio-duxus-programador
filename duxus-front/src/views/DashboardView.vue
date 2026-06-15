<script setup lang="ts">
import { ref, onMounted } from 'vue'
import api from '@/api/api'

const integranteMaisUsado = ref<any>(null)
const clubeMaisRecorrente = ref<string>('')
const funcaoMaisRecorrente = ref<string>('')
const contagemClubes = ref<Record<string, number>>({})
const contagemFuncoes = ref<Record<string, number>>({})
const integrantesTimeMaisRecorrente = ref<string[]>([])
const loading = ref(true)

onMounted(async () => {
  try {
    const [integrante, clube, funcao, clubes, funcoes, timeMaisRecorrente] = await Promise.all([
      api.get('/api/times/integrante-mais-usado'),
      api.get('/api/times/clube-mais-recorrente'),
      api.get('/api/times/funcao-mais-recorrente'),
      api.get('/api/times/contagem-clubes'),
      api.get('/api/times/contagem-funcoes'),
      api.get('/api/times/integrantes-time-mais-recorrente')
    ])

    integranteMaisUsado.value = integrante.data
    clubeMaisRecorrente.value = clube.data
    funcaoMaisRecorrente.value = funcao.data
    contagemClubes.value = clubes.data
    contagemFuncoes.value = funcoes.data
    integrantesTimeMaisRecorrente.value = timeMaisRecorrente.data
  } catch (e) {
    console.error('Erro ao carregar dashboard:', e)
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div>
    <h2 class="page-title">Dashboard</h2>

    <div v-if="loading" class="loading">Carregando dados...</div>

    <div v-else class="dashboard-grid">
      <div class="card">
        <span class="card-label">Integrante Mais Usado</span>
        <strong class="card-value">{{ integranteMaisUsado?.nome || '-' }}</strong>
        <span class="card-subtext">{{ integranteMaisUsado?.funcao || '-' }}</span>
      </div>

      <div class="card">
        <span class="card-label">Clube Mais Recorrente</span>
        <strong class="card-value">{{ clubeMaisRecorrente || '-' }}</strong>
      </div>

      <div class="card">
        <span class="card-label">Função Mais Recorrente</span>
        <strong class="card-value">{{ funcaoMaisRecorrente || '-' }}</strong>
      </div>

      <div class="card">
        <h3>Time Mais Recorrente</h3>
        <ul class="list" v-if="integrantesTimeMaisRecorrente.length">
          <li v-for="nome in integrantesTimeMaisRecorrente" :key="nome">{{ nome }}</li>
        </ul>
        <p v-else class="empty-text">Nenhum registro encontrado.</p>
      </div>

      <div class="card">
        <h3>Aparições por Clube</h3>
        <div class="list" v-if="Object.keys(contagemClubes).length">
          <div v-for="(qtd, clube) in contagemClubes" :key="clube" class="list-item">
            <span>{{ clube }}</span>
            <span class="count">{{ qtd }}x</span>
          </div>
        </div>
        <p v-else class="empty-text">Nenhum registro encontrado.</p>
      </div>

      <div class="card">
        <h3>Contagem por Função</h3>
        <div class="list" v-if="Object.keys(contagemFuncoes).length">
          <div v-for="(qtd, funcao) in contagemFuncoes" :key="funcao" class="list-item">
            <span>{{ funcao }}</span>
            <span class="count">{{ qtd }}x</span>
          </div>
        </div>
        <p v-else class="empty-text">Nenhum registro encontrado.</p>
      </div>
    </div>
  </div>
</template>

<style scoped>
.page-title {
  font-size: 1.5rem;
  margin-bottom: 1.5rem;
}

.loading {
  color: #8d8d99;
}

.dashboard-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1rem;
}

@media (max-width: 768px) {
  .dashboard-grid {
    grid-template-columns: 1fr;
  }
}

.card {
  background: #202024;
  border: 1px solid #323238;
  border-radius: 6px;
  padding: 1.25rem;
}

.card h3 {
  font-size: 1rem;
  margin-bottom: 1rem;
  color: #e1e1e6;
  border-bottom: 1px solid #323238;
  padding-bottom: 0.5rem;
}

.card-label {
  display: block;
  font-size: 0.85rem;
  color: #8d8d99;
  margin-bottom: 0.5rem;
}

.card-value {
  display: block;
  font-size: 1.35rem;
  color: #ffffff;
}

.card-subtext {
  display: block;
  font-size: 0.85rem;
  color: #8d8d99;
  margin-top: 0.25rem;
}

.list {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 0;
  margin: 0;
  list-style: none;
}

.list-item, .list li {
  display: flex;
  justify-content: space-between;
  background: #121214;
  padding: 0.6rem 0.85rem;
  border-radius: 4px;
  font-size: 0.9rem;
}

.count {
  font-weight: bold;
  color: #00b37e;
}

.empty-text {
  font-size: 0.9rem;
  color: #8d8d99;
}
</style>