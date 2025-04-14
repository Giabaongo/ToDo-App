import React, { useState, useEffect } from 'react';
import { 
  Box, 
  Typography, 
  List, 
  ListItem, 
  ListItemText, 
  ListItemSecondaryAction,
  IconButton,
  TextField,
  Button,
  Paper,
  Checkbox,
  AppBar,
  Toolbar
} from '@mui/material';
import { Delete as DeleteIcon, Edit as EditIcon, Logout as LogoutIcon } from '@mui/icons-material';
import { todoService } from '../../services/todoService';
import { authService } from '../../services/authService';
import { useNavigate } from 'react-router-dom';

export const TodoList = () => {
  const [todos, setTodos] = useState([]);
  const [newTodo, setNewTodo] = useState('');
  const [editingTodo, setEditingTodo] = useState(null);
  const [editText, setEditText] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    fetchTodos();
  }, []);

  const handleLogout = () => {
    authService.logout();
    navigate('/login');
  };

  const fetchTodos = async () => {
    try {
      const data = await todoService.getAll();
      setTodos(data);
    } catch (error) {
      console.error('Error fetching todos:', error);
    }
  };

  const handleAddTodo = async () => {
    if (newTodo.trim()) {
      try {
        await todoService.create(newTodo.trim());
        setNewTodo('');
        fetchTodos();
      } catch (error) {
        console.error('Error adding todo:', error);
      }
    }
  };

  const handleDeleteTodo = async (id) => {
    try {
      await todoService.delete(id);
      fetchTodos();
    } catch (error) {
      console.error('Error deleting todo:', error);
    }
  };

  const handleEditTodo = async (id) => {
    if (editText.trim()) {
      try {
        await todoService.update(id, { title: editText.trim() });
        setEditingTodo(null);
        setEditText('');
        fetchTodos();
      } catch (error) {
        console.error('Error updating todo:', error);
      }
    }
  };

  const handleToggleComplete = async (id, completed) => {
    try {
      const todo = todos.find(t => t.id === id);
      if (todo) {
        await todoService.update(id, { 
          title: todo.title,
          completed: completed 
        });
        fetchTodos();
      }
    } catch (error) {
      console.error('Error toggling todo status:', error);
    }
  };

  return (
    <Box>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Todo List
          </Typography>
          <Button 
            color="inherit" 
            startIcon={<LogoutIcon />}
            onClick={handleLogout}
          >
            Logout
          </Button>
        </Toolbar>
      </AppBar>

      <Box sx={{ maxWidth: 800, margin: 'auto', mt: 4, px: 2 }}>
        <Paper sx={{ p: 2, mb: 2 }}>
          <Box sx={{ display: 'flex', gap: 1 }}>
            <TextField
              fullWidth
              variant="outlined"
              placeholder="Add new todo..."
              value={newTodo}
              onChange={(e) => setNewTodo(e.target.value)}
              onKeyPress={(e) => e.key === 'Enter' && handleAddTodo()}
            />
            <Button 
              variant="contained" 
              onClick={handleAddTodo}
              disabled={!newTodo.trim()}
            >
              Add
            </Button>
          </Box>
        </Paper>

        <List>
          {todos.map((todo) => (
            <ListItem
              key={todo.id}
              sx={{
                bgcolor: 'background.paper',
                mb: 1,
                borderRadius: 1,
                boxShadow: 1
              }}
            >
              <Checkbox
                checked={todo.completed}
                onChange={(e) => handleToggleComplete(todo.id, e.target.checked)}
                color="primary"
              />
              {editingTodo === todo.id ? (
                <Box sx={{ display: 'flex', flexGrow: 1, gap: 1 }}>
                  <TextField
                    fullWidth
                    value={editText}
                    onChange={(e) => setEditText(e.target.value)}
                    onKeyPress={(e) => e.key === 'Enter' && handleEditTodo(todo.id)}
                  />
                  <Button 
                    variant="contained" 
                    onClick={() => handleEditTodo(todo.id)}
                    disabled={!editText.trim()}
                  >
                    Save
                  </Button>
                  <Button 
                    variant="outlined" 
                    onClick={() => {
                      setEditingTodo(null);
                      setEditText('');
                    }}
                  >
                    Cancel
                  </Button>
                </Box>
              ) : (
                <>
                  <ListItemText
                    primary={todo.title}
                    sx={{
                      textDecoration: todo.completed ? 'line-through' : 'none',
                      color: todo.completed ? 'text.secondary' : 'text.primary'
                    }}
                  />
                  <ListItemSecondaryAction>
                    <IconButton
                      edge="end"
                      aria-label="edit"
                      onClick={() => {
                        setEditingTodo(todo.id);
                        setEditText(todo.title);
                      }}
                    >
                      <EditIcon />
                    </IconButton>
                    <IconButton
                      edge="end"
                      aria-label="delete"
                      onClick={() => handleDeleteTodo(todo.id)}
                    >
                      <DeleteIcon />
                    </IconButton>
                  </ListItemSecondaryAction>
                </>
              )}
            </ListItem>
          ))}
        </List>
      </Box>
    </Box>
  );
}; 