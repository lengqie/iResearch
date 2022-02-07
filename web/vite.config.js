import vue from '@vitejs/plugin-vue'

export default {
    base: './',
    plugins: [vue()],
    optimizeDeps: {
    },
    server: {
        cors: true,
        proxy: {
          '/api': {
              target: 'http://localhost:8080',
              changeOrigin: true,
              rewrite: (path) => path.replace(/^\/api/, '')
          }
        }
    }
}