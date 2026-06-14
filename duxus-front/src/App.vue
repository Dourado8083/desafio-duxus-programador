<script setup>
</script>

<template>
  <div>
    <nav style="padding: 1rem; background: #333; color: white;">
      <router-link to="/integrantes" style="color: white; margin-right: 1rem;">Integrantes</router-link>
      <router-link to="/times" style="color: white;">Times</router-link>
    </nav>
    <router-view />
  </div>
</template>