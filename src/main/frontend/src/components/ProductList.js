import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Grid } from '@mui/material';
import ProductCard from './ProductCard';

const ProductsList = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/products');
        setProducts(response.data);
      } catch (error) {
        console.error('Error fetching products:', error);
      }
    };

    fetchProducts();
  }, []);

  return (
    <Grid container justifyContent="center" spacing={2}> {/* Add spacing for better visual separation */}
      {products.map(product => (
        <Grid item xs={12} sm={6} md={4} key={product.id}> {/* Adjusted for 3 items per row on medium devices and up */}
          <ProductCard product={product} />
        </Grid>
      ))}
    </Grid>
  );
};

export default ProductsList;
