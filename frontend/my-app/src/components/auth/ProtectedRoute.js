import React from 'react';
import { Navigate } from 'react-router-dom';
import { authService } from '../../services/authService';

export const ProtectedRoute = ({ children }) => {
  if (!authService.isAuthenticated()) {
    return <Navigate to="/login" />;
  }

  return children;
}; 