import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import { CssBaseline, Container } from '@mui/material';
import NavBar from './NavBar';
import HomePage from "./components/HomePage"
import CategoriesPage from "./components/CategoriesPage"
import ContactPage from "./components/ContactPage"




function App() {
  return (
    <Router>
      <CssBaseline />
      <NavBar />
      <Container>
        <Routes>
          <Route path="/" element={<HomePage/>} />
          <Route path="/categories" element={<CategoriesPage />} />
          <Route path="/contact" element={<ContactPage />} />
        </Routes>
      </Container>
    </Router>
  );
}

export default App;