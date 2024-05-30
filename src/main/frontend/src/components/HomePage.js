import React from 'react';
import { Container, Box } from '@mui/material';
import ProductsList from "./ProductList";
import Chatbox from './Chatbox';

export const HomePage = () => {  

    return (
        <Container>
          <h1>Our Products</h1>
          <ProductsList/>
          {/* Positioning the chatbox */}
          <Box sx={{
            position: 'fixed',
            bottom: 16, // Adjust space from bottom
            right: 16, // Adjust space from right
            zIndex: 1000, // Ensure it's above other content
          }}>
             <Chatbox/>
          </Box>
        </Container>
      );

};

export default HomePage;




