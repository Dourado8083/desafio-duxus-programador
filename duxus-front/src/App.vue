<template>
  <div class="app-layout">
    <nav class="navbar">
      <div class="nav-links">
        <router-link to="/dashboard" class="nav-item">Dashboard</router-link>
        <router-link to="/integrantes" class="nav-item">Integrantes</router-link>
        <router-link to="/times" class="nav-item">Times</router-link>
      </div>
    </nav>

    <main class="container">
      <router-view />
    </main>
  </div>
</template>

<style scoped>
.app-layout {
  min-height: 100vh;
  background-color: #121214;
  color: #e1e1e6;
  font-family: system-ui, -apple-system, sans-serif;
}

.navbar {
  display: flex;
  align-items: center;
  padding: 1rem 2rem;
  background-color: #202024;
  border-bottom: 1px solid #323238;
}

.nav-links {
  display: flex;
  gap: 1.5rem;
}

.nav-item {
  color: #a8a8b3;
  text-decoration: none;
  font-size: 0.95rem;
  font-weight: 500;
  padding: 0.5rem;
  transition: color 0.2s;
}

.nav-item:hover {
  color: #ffffff;
}

.router-link-active {
  color: #00b37e;
  border-bottom: 2px solid #00b37e;
}

.container {
  max-width: 1100px;
  margin: 0 auto;
  padding: 2rem 1.5rem;
}
</style>