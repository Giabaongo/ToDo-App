import api from './api';

export const todoService = {
  getAll: async () => {
    const response = await api.get('/api/todos');
    return response.data;
  },

  create: async (title) => {
    const response = await api.post('/api/todos', { title });
    return response.data;
  },

  update: async (id, data) => {
    const response = await api.put(`/api/todos/${id}`, data);
    return response.data;
  },

  delete: async (id) => {
    await api.delete(`/api/todos/${id}`);
  },

  toggleComplete: async (id, completed) => {
    const response = await api.put(`/api/todos/${id}`, { completed });
    return response.data;
  }
}; 